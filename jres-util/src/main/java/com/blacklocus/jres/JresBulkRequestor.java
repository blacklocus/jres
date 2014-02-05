/**
 * Copyright 2013 BlackLocus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blacklocus.jres;

import com.blacklocus.jres.request.JresBulkable;
import com.blacklocus.jres.request.bulk.JresBulk;
import com.blacklocus.jres.response.bulk.JresBulkItemResult;
import com.blacklocus.jres.response.bulk.JresBulkReply;
import com.blacklocus.jres.util.DaemonThreadFactory;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.SettableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Helper that aggregates {@link JresBulkable} operations and submits them in batch according to constructor parameters.
 *
 * <p>Be sure to {@link #start()} this requestor. This requestor can be properly shutdown if such behavior is desired
 * through {@link #close()}. Allow ample time for the buffers to flush and submit to ElasticSearch (see
 * {@link #CLOSING_LENIENCY_SECONDS}).
 */
public class JresBulkRequestor implements Runnable, Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(JresBulkRequestor.class);

    /** Polling lenience, how long in seconds each poll to the transfer queue is willing to wait. */
    public static final int POLLING_LENIENCY_SECONDS = 5;
    /** Amount of time {@link #close()} should wait for buffers to flush and submit before excepting. */
    public static final int CLOSING_LENIENCY_SECONDS = 60;


    private final int batchSize;
    private final int sleepIntervalMs;

    private final String targetIndex;
    private final String targetType;
    private final Jres jres;

    private final Integer numThreads;
    private final ExecutorService executorService;
    private final List<Future<?>> indexerWorkerFutures = new ArrayList<Future<?>>();

    private final BlockingQueue<FuturedDocument> q;


    /**
     * Create a JresBulkRequestor which does not default to any particular index nor type. This means each received
     * JresBulkable must specify their index and type.
     *
     * <p>Be sure to {@link #start()} this requestor
     */
    public JresBulkRequestor(int batchSize, int sleepIntervalMs, int numThreads, Jres jres) {
        this(batchSize, sleepIntervalMs, numThreads, null, null, jres);
    }

    /**
     * Create a JresBulkRequestor defaulting to a particular index. This means all received
     * JresBulkable must specify their type, but if their index is not specified will default to the one given here.
     *
     * <p>Be sure to {@link #start()} this requestor
     */
    public JresBulkRequestor(int batchSize, int sleepIntervalMs, int numThreads, @Nullable String targetIndex, Jres jres) {
        this(batchSize, sleepIntervalMs, numThreads, targetIndex, null, jres);
    }

    /**
     * Create a JresBulkRequestor defaulting to a particular index. This means JresBulkable that specify their index
     * or type will be placed there. But for unspecified will default to the parameters given here.
     *
     * <p>Be sure to {@link #start()} this requestor
     */
    public JresBulkRequestor(int batchSize, int sleepIntervalMs, int numThreads,
                             @Nullable String targetIndex, @Nullable String targetType, Jres jres) {
        this.batchSize = batchSize;
        this.sleepIntervalMs = sleepIntervalMs;
        this.numThreads = numThreads;

        this.targetIndex = targetIndex;
        this.targetType = targetType;
        this.jres = jres;

        this.executorService = new ThreadPoolExecutor(
                2, 2, 1, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new DaemonThreadFactory()
        );

        this.q = new SynchronousQueue<FuturedDocument>(true);
    }

    /**
     * @return starts the configured number of threads.
     */
    public JresBulkRequestor start() {
        for (int i = 0; i < numThreads; i++) {
            indexerWorkerFutures.add(executorService.submit(this));
        }
        return this;
    }


    /**
     * @return a future which can be used to block the calling thread until the receiving end signals that the
     * received document has been successfully submitted in a bulk request to ElasticSearch. If ElasticSearch
     * indicates an error for this particular ProductPage in the request, the returned {@link Future#get()} will except.
     */
    public Future<?> put(JresBulkable bulkable) {
        FuturedDocument futuredDocument = new FuturedDocument(bulkable);
        try {
            q.put(futuredDocument);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return futuredDocument.future;
    }

    /**
     * @return a future which can be used to block the calling thread until the receiving end signals that the
     * received document has been successfully submitted in a bulk request to ElasticSearch. If ElasticSearch
     * indicates an error for this particular ProductPage in the request, the returned {@link Future#get()} will except.
     * <code>null</code> if the offer failed to commit in the timeout allotted.
     */
    public Future<?> offer(JresBulkable bulkable, long timeout, TimeUnit unit) {
        FuturedDocument futuredDocument = new FuturedDocument(bulkable);
        final boolean accepted;
        try {
            accepted = q.offer(futuredDocument, timeout, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return accepted ? futuredDocument.future : null;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (!Thread.currentThread().isInterrupted()) {
            try {
                indexNextBatch();

            } catch (InterruptedException e) {
                LOG.error("Worker interrupted ungracefully. Shutting worker down.", 3);
                Thread.currentThread().interrupt(); // reset interrupted status
            } catch (Exception e) {
                String msg = "error in indexer worker, last batch may have been lost";
                LOG.error(msg, e);
            }
        }
        LOG.debug("Worker interrupted. Shutting worker down.");
    }

    private void indexNextBatch() throws InterruptedException {

        List<JresBulkable> bulk = new ArrayList<JresBulkable>(batchSize);
        List<SettableFuture<JresBulkItemResult>> futures = new ArrayList<SettableFuture<JresBulkItemResult>>(batchSize);

        int numDocs = 0;
        FuturedDocument futuredDocument;
        // Poll with a long-enough timeout to promote batch aggregation, but not so long as to keep things in limbo
        // for a long time.
        while (numDocs < batchSize && null != (futuredDocument = poll())) {
            futures.add(futuredDocument.future);
            bulk.add(futuredDocument.bulkable);
            ++numDocs;
        }

        if (bulk.size() > 0) {
            assert numDocs > 0;
            LOG.info("Submitting bulk index of " + numDocs + " products.");
            if (LOG.isDebugEnabled()) {
                LOG.debug(bulk.toString());
            }

            JresBulkReply bulkReply = jres.quest(new JresBulk(targetIndex, targetType, bulk));
            List<JresBulkItemResult> results = Lists.newArrayList(bulkReply.getResults());
            assert futures.size() == results.size();

            for (int i = 0; i < results.size(); i++) {
                SettableFuture<JresBulkItemResult> future = futures.get(i);
                JresBulkItemResult result = results.get(i);
                if (result.getResult().hasError()) {
                    future.setException(new RuntimeException(result.getResult().getError()));
                } else {
                    future.set(result);
                }
            }

        } else {
            // reading too hard, sleep a bit
            Thread.sleep(sleepIntervalMs);
        }
    }

    /**
     * Guards against InterruptedException so that {@link #indexNextBatch()} can submit its current batch. The
     * main loop in {@link #run()} checks for interrupted status and should exit soon.
     *
     * @return q.poll result or null if none was available within the time limit
     */
    private FuturedDocument poll() {
        try {
            return q.poll(POLLING_LENIENCY_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.debug("Poll interrupted. Absorbing this exception. Main loop in this thread should be checking " +
                    "interrupted status and stop the worker in a moment.", e);
            Thread.currentThread().interrupt(); // reset interrupted status
            return null;
        }
    }

    /**
     * Waits for the internal document indexing queue to empty. Then interrupts IndexerWorkers which should be coded to
     * handle interrupts. The IndexerWorkers should then submit their currently pending batch of documents in their
     * final ElasticSearch _bulk request and exit normally.
     *
     * @throws IOException particularly, if interrupted
     */
    @Override
    public void close() throws IOException {
        LOG.info("Shutting down BulkIndexers");
        while (!q.isEmpty()) {
            LOG.info("Waiting for work q to empty.");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                LOG.warn("Interrupted while shutting down. You interrupted the interrupter! Ignoring.", e);
            }
        }
        try {
            for (Future<?> indexerWorkerFuture : indexerWorkerFutures) {
                indexerWorkerFuture.cancel(true);
            }
            executorService.shutdown();
            executorService.awaitTermination(CLOSING_LENIENCY_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.warn("Failed to stop worker threads. Pool may fail to shutdown.", e);
        }
        LOG.info("IndexerWorkers shut down.");
    }

    private static class FuturedDocument {
        final SettableFuture<JresBulkItemResult> future;
        final JresBulkable bulkable;

        FuturedDocument(JresBulkable bulkable) {
            this.future = SettableFuture.create();
            this.bulkable = bulkable;
        }
    }
}
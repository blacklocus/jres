package com.blacklocus.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExceptingRunnable implements Runnable {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public final void run() {
        try {
            go();
        } catch (Throwable t) {
            LOG.error(toString(), t);
        }
    }

    protected abstract void go() throws Exception;
}

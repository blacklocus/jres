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
package com.blacklocus.jres.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * Produces HttpClient instances based on a thread-safe connection pool.
 *
 * @author Jason Dunkelberger (dirkraft)
 */
public class HttpClientFactory {

    public static final int POOL_MAX_PER_ROUTE = 32;
    public static final int POOL_MAX_TOTAL = 256;

    public static final PoolingHttpClientConnectionManager POOL_MGR = new PoolingHttpClientConnectionManager(
            5, TimeUnit.MINUTES
    );
    static {
        POOL_MGR.setDefaultMaxPerRoute(POOL_MAX_PER_ROUTE);
        POOL_MGR.setMaxTotal(POOL_MAX_TOTAL);
    }

    /**
     * Construct a new HttpClient which uses the {@link #POOL_MGR default connection pool}.
     *
     * @param connectionTimeoutMs highly sensitive to application so must be specified
     * @param socketTimeoutMs     highly sensitive to application so must be specified
     */
    public static HttpClient create(final int connectionTimeoutMs, final int socketTimeoutMs) {
        return HttpClientBuilder.create()
                .setConnectionManager(POOL_MGR)
                .setDefaultRequestConfig(
                        RequestConfig.copy(RequestConfig.DEFAULT)
                                .setConnectTimeout(connectionTimeoutMs)
                                .setSocketTimeout(socketTimeoutMs)
                                .build()
                ).build();
    }

}

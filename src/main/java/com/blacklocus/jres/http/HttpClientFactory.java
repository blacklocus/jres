package com.blacklocus.jres.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
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
                .setDefaultRequestConfig(
                        RequestConfig.copy(RequestConfig.DEFAULT)
                                .setConnectTimeout(connectionTimeoutMs)
                                .setSocketTimeout(socketTimeoutMs)
                                .build()
                ).build();
    }

}

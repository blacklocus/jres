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
package com.blacklocus.jres.metrics;

import com.blacklocus.jres.Jres;
import com.blacklocus.jres.request.JresRequest;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JresMetrics {

    Jres jres;
    MetricRegistry registry;
    Function<JresRequest<?>, String> metricNameFn = new Function<JresRequest<?>, String>() {
        @Override
        public String apply(JresRequest<?> req) {
            return "jresRequest";
        }
    };

    /**
     * @param jres that should be decorated with metrics
     */
    public JresMetrics withJres(Jres jres) {
        this.jres = jres;
        return this;
    }

    /**
     * @param registry which should be associated with generated metrics.
     */
    public JresMetrics withRegistry(MetricRegistry registry) {
        this.registry = registry;
        return this;
    }

    /**
     * @param metricNameFn that is given the {@link JresRequest} and returns a corresponding name for the timer metric.
     *                     Defaults to always returning the same metric name, "jresRequest".
     */
    public JresMetrics withMetricNameFn(Function<JresRequest<?>, String> metricNameFn) {
        this.metricNameFn = metricNameFn;
        return this;
    }

    /**
     * @return a Jres which collects metrics {@link #withRegistry(MetricRegistry)}
     */
    public Jres build() {

        Preconditions.checkArgument(jres != null, "A `jres` instance is required.");
        Preconditions.checkArgument(registry != null, "A metric `registry` is required.");
        Preconditions.checkArgument(metricNameFn != null, "A metric name function (`metricNameFn`) is required.");

        return (Jres) Proxy.newProxyInstance(JresMetrics.class.getClassLoader(), new Class<?>[]{Jres.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // All Jres method first arguments are subclasses of JresRequest.
                JresRequest request = (JresRequest) args[0];
                String metricName = metricNameFn.apply(request);
                Timer.Context context = null;
                if (metricName != null) {
                    context = registry.timer(metricName).time();
                }
                try {
                    return method.invoke(jres, args);
                } finally {
                    if (context != null) {
                        context.stop();
                    }
                }
            }
        });
    }
}

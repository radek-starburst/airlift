/*
 * Copyright 2010 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.http.client;

import com.google.common.collect.ImmutableSet;
import io.airlift.units.Duration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AsyncHttpClientTest
        extends AbstractHttpClientTest
{
    private AsyncHttpClient httpClient;

    @BeforeMethod
    public void setUp()
            throws Exception
    {
        httpClient = new ApacheAsyncHttpClient(new HttpClientConfig().setKeepAliveInterval(new Duration(1, TimeUnit.MINUTES)),
                ImmutableSet.of(new TestingRequestFilter()));
    }

    @Override
    public <T, E extends Exception> T executeRequest(Request request, ResponseHandler<T, E> responseHandler)
            throws Exception
    {
        return httpClient.executeAsync(request, responseHandler).checkedGet();
    }

    @Override
    public <T, E extends Exception> T  executeRequest(HttpClientConfig config, Request request, ResponseHandler<T, E> responseHandler)
            throws Exception
    {
        AsyncHttpClient client = new ApacheAsyncHttpClient(config);
        return client.executeAsync(request, responseHandler).checkedGet();
    }

    @Test(enabled = false, description = "Apache async client doesn't timeout correctly")
    @Override
    public void testConnectNoRead()
            throws Exception
    {
    }

    @Test(enabled = false, description = "Apache async client doesn't timeout correctly")
    @Override
    public void testConnectReadIncomplete()
            throws Exception
    {
    }
}

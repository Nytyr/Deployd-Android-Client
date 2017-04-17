package me.nytyr.deployd.android.requester.http;

import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class BaseRequester implements Requester {

    private int timeoutInMilliseconds;

    public BaseRequester(
            int timeoutInMilliseconds
    ) {
        this.timeoutInMilliseconds = timeoutInMilliseconds;
    }

    public abstract HttpRequestBase getRequestBase(final Request request);

    @Override
    public Response run(final Request request) throws HttpRequesterException {
        try {
            final HttpRequestBase requestBase = getRequestBase(request);
            requestBase.setConfig(
                    RequestConfig.custom()
                            .setSocketTimeout(timeoutInMilliseconds)
                            .setConnectTimeout(timeoutInMilliseconds)
                            .setConnectionRequestTimeout(timeoutInMilliseconds)
                            .build()
            );

            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("http.protocol.version", HttpVersion.HTTP_1_1);
            requestBase.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

            for (String key : request.headers.keySet()) {
                requestBase.addHeader(key,request.headers.get(key));
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(requestBase);
            int statusCode = response.getStatusLine().getStatusCode();
            if (response.getEntity() != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
                StringBuilder buffer = new StringBuilder();
                String output;
                while ((output = bufferedReader.readLine()) != null) {
                    buffer.append(output);
                }
                httpClient.close();
                return new Response(buffer.toString(), statusCode);
            } else {
                httpClient.close();
                return new Response("", statusCode);
            }

        } catch (Exception e) {
            throw new HttpRequesterException(e);
        }
    }
}

package me.nytyr.deployd.android.requester.http;

import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;

public class GetRequester extends BaseRequester {

    public GetRequester(int timeoutInMilliseconds) {
        super(timeoutInMilliseconds);
    }

    @Override
    public HttpRequestBase getRequestBase(Request request) {
        return new HttpGet(request.url);
    }
}

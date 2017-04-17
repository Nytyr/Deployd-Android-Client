package me.nytyr.deployd.android.requester.http;

import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;

public class DeleteRequester extends BaseRequester {

    public DeleteRequester(int timeoutInMilliseconds) {
        super(timeoutInMilliseconds);
    }

    @Override
    public HttpRequestBase getRequestBase(Request request) {
        return new HttpDelete(request.url);
    }
}

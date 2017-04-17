package me.nytyr.deployd.android.requester.http;

import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

public class PutRequester extends BaseRequester {

    public PutRequester(int timeoutInMilliseconds) {
        super(timeoutInMilliseconds);
    }

    @Override
    public HttpRequestBase getRequestBase(Request request) {
        HttpPut httpPut = new HttpPut(request.url);
        StringEntity entity = new StringEntity(request.body, "UTF-8");
        entity.setContentType(new BasicHeader("Content-Type", "application/json"));
        httpPut.setEntity(entity);
        return httpPut;
    }
}

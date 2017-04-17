package me.nytyr.deployd.android.requester.http;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpRequestBase;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

public class PostRequester extends BaseRequester {

    public PostRequester(int timeoutInMilliseconds) {
        super(timeoutInMilliseconds);
    }

    @Override
    public HttpRequestBase getRequestBase(Request request) {
        HttpPost httpPost = new HttpPost(request.url);
        StringEntity entity = new StringEntity(request.body, "UTF-8");
        entity.setContentType(new BasicHeader("Content-Type", "application/json"));
        httpPost.setEntity(entity);
        return httpPost;
    }
}

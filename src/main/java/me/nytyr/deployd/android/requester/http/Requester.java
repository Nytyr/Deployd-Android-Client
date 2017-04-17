package me.nytyr.deployd.android.requester.http;

import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;

public interface Requester {
    public Response run(final Request request) throws HttpRequesterException;
}

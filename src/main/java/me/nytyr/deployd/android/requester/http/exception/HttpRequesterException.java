package me.nytyr.deployd.android.requester.http.exception;

import java.io.IOException;

public class HttpRequesterException extends IOException {
    public HttpRequesterException(Exception e) {
        super(e);
    }
}

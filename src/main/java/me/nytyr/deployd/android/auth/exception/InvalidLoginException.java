package me.nytyr.deployd.android.auth.exception;

import java.io.IOException;

public class InvalidLoginException extends IOException {

    private int statusCode;
    private String message;

    public InvalidLoginException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return message;
    }
}

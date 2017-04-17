package me.nytyr.deployd.android.requester.http;


import java.util.HashMap;
import java.util.Map;

public class Request {

    public String url;
    public String body;
    public Map<String, String> headers;

    public Request(String url, String body) {
        this(url, body, new HashMap<String, String>());
    }

    public Request(String url, String body, Map<String, String> headers) {
        this.url = url;
        this.body = body;
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (body != null ? !body.equals(request.body) : request.body != null) return false;
        if (headers != null ? !headers.equals(request.headers) : request.headers != null) return false;
        if (url != null ? !url.equals(request.url) : request.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                '}';
    }
}

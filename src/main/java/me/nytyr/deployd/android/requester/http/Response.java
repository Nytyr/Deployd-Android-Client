package me.nytyr.deployd.android.requester.http;

public class Response {

    public String body;
    public int statusCode;

    public Response(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (statusCode != response.statusCode) return false;
        if (body != null ? !body.equals(response.body) : response.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = body != null ? body.hashCode() : 0;
        result = 31 * result + statusCode;
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "body='" + body + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}

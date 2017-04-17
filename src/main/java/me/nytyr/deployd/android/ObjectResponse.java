package me.nytyr.deployd.android;

import me.nytyr.deployd.android.requester.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjectResponse {

    public JSONObject body;
    public int statusCode;

    public ObjectResponse(JSONObject body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public static ObjectResponse init(Response response) throws JSONException {
        return new ObjectResponse(
                new JSONObject(response.body),
                response.statusCode
        );
    }

    @Override
    public String toString() {
        return "ObjectResponse{" +
                "body=" + body +
                ", statusCode=" + statusCode +
                '}';
    }
}

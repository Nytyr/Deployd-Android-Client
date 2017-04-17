package me.nytyr.deployd.android;

import me.nytyr.deployd.android.requester.http.Response;
import org.json.JSONArray;
import org.json.JSONException;

public class ArrayResponse {

    public JSONArray body;
    public int statusCode;

    public ArrayResponse(JSONArray body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public static ArrayResponse init(Response response) throws JSONException {
        return new ArrayResponse(
                new JSONArray(response.body),
                response.statusCode
        );
    }
}

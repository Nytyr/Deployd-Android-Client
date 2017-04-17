package me.nytyr.deployd.android.requester.http;

import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;
import me.nytyr.deployd.android.requester.http.PutRequester;
import me.nytyr.deployd.android.requester.http.Request;
import me.nytyr.deployd.android.requester.http.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PutRequesterTest {
    @Test
    public void givenValidData_WhenRun_ShouldReturnValidBody() throws HttpRequesterException, JSONException {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value");
        Response response = new PutRequester(20000).run(
                new Request("http://httpbin.org/put?id=27", "{\"foo\":\"bar\"}", headers)
        );

        JSONObject body = new JSONObject(response.body);
        assertThat(200, is(response.statusCode));
        JSONObject expected = new JSONObject();
        expected.put("data", "{\"foo\":\"bar\"}");
        expected.put("args", new JSONObject().put("id", "27"));
        expected.put("url", "http://httpbin.org/put?id=27");
        expected.put("headers", new JSONObject().put("Header", "value"));
        JSONAssert.assertEquals(expected, body, false);
    }
}

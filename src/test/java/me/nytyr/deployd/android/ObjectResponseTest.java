package me.nytyr.deployd.android;

import me.nytyr.deployd.android.requester.http.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ObjectResponseTest {

    @Test
    public void givenValidResponse_WhenInit_ShouldCreateValidObjectResponse() throws JSONException {
        ObjectResponse objectResponse = new ObjectResponse(
                new JSONObject().put("foo", "bar"),
                200
        );
        ObjectResponse expected = ObjectResponse.init(new Response("{\"foo\":\"bar\"}", 200));
        assertThat(
                objectResponse.statusCode,
                is(expected.statusCode)
        );
        JSONAssert.assertEquals(
                "{\"foo\":\"bar\"}",
                objectResponse.body,
                true
        );
    }
}

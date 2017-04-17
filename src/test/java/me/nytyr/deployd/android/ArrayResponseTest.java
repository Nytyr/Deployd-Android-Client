package me.nytyr.deployd.android;

import me.nytyr.deployd.android.requester.http.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArrayResponseTest {

    @Test
    public void givenValidResponse_WhenInit_ShouldCreateValidObjectResponse() throws JSONException {
        ArrayResponse objectResponse = new ArrayResponse(
                new JSONArray().put("foo").put("bar"),
                200
        );
        ArrayResponse expected = ArrayResponse.init(new Response("[\"foo\",\"bar\"]", 200));
        assertThat(
                objectResponse.statusCode,
                is(expected.statusCode)
        );
        JSONAssert.assertEquals(
                "[\"foo\",\"bar\"]",
                objectResponse.body,
                true
        );
    }
}

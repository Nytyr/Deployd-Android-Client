package me.nytyr.deployd.android;

import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;
import me.nytyr.deployd.android.requester.http.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CollectionTest {

    @Test
    public void whenGet_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        GetRequester getRequester = mock(GetRequester.class);
        when(getRequester.run(any(Request.class))).thenReturn(
                new Response("[]",200)
        );
        new Collection(
                "http://server/",
                getRequester,
                mock(PostRequester.class),
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).get();

        verify(getRequester).run(new Request(
                "http://server/collection",
                "",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenGet_ShouldReturnValidResponse() throws JSONException, HttpRequesterException {
        GetRequester getRequester = mock(GetRequester.class);
        when(getRequester.run(any(Request.class))).thenReturn(
                new Response("[1,2,3]", 200)
        );
        ArrayResponse response = new Collection(
                "http://server/",
                getRequester,
                mock(PostRequester.class),
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).get();
        assertThat(response.statusCode, is(200));
        JSONAssert.assertEquals(
                "[1,2,3]",
                response.body,
                true
        );
    }

    @Test
    public void whenGetById_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        GetRequester getRequester = mock(GetRequester.class);
        when(getRequester.run(any(Request.class))).thenReturn(
                new Response("{}",200)
        );
        new Collection(
                "http://server/",
                getRequester,
                mock(PostRequester.class),
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).get("::id::");

        verify(getRequester).run(new Request(
                "http://server/collection/::id::",
                "",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenGetById_ShouldReturnValidResponse() throws JSONException, HttpRequesterException {
        GetRequester getRequester = mock(GetRequester.class);
        when(getRequester.run(any(Request.class))).thenReturn(
                new Response("{\"number\":1}", 200)
        );
        ObjectResponse response = new Collection(
                "http://server/",
                getRequester,
                mock(PostRequester.class),
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).get("::id::");
        assertThat(response.statusCode, is(200));
        JSONAssert.assertEquals(
                "{\"number\":1}",
                response.body,
                true
        );
    }

    @Test
    public void whenGetByObject_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        GetRequester getRequester = mock(GetRequester.class);
        when(getRequester.run(any(Request.class))).thenReturn(
                new Response("[]",200)
        );
        JSONObject query = new JSONObject("{author: {$ne: \"Bob\"}}");
        new Collection(
                "http://server/",
                getRequester,
                mock(PostRequester.class),
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).get(query);
        verify(getRequester).run(new Request(
                "http://server/collection?%7B%22author%22%3A%7B%22%24ne%22%3A%22Bob%22%7D%7D",
                "",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenGetByObject_ShouldReturnValidResponse() throws JSONException, HttpRequesterException {
        GetRequester getRequester = mock(GetRequester.class);
        when(getRequester.run(any(Request.class))).thenReturn(
                new Response("[1]", 200)
        );
        ArrayResponse response = new Collection(
                "http://server/",
                getRequester,
                mock(PostRequester.class),
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).get(new JSONObject());
        assertThat(response.statusCode, is(200));
        JSONAssert.assertEquals(
                "[1]",
                response.body,
                true
        );
    }

    @Test
    public void whenPost_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        PostRequester postRequester = mock(PostRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(
                new Response("{}",200)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                postRequester,
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).post(data);
        verify(postRequester).run(new Request(
                "http://server/collection",
                "{\"name\":\"foo\"}",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenPost_ShouldReturnValidResponse() throws JSONException, HttpRequesterException {
        PostRequester postRequester = mock(PostRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(
                new Response("{\"id\":\"::id::\"}",201)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        ObjectResponse response = new Collection(
                "http://server/",
                mock(GetRequester.class),
                postRequester,
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).post(data);
        assertThat(response.statusCode, is(201));
        JSONAssert.assertEquals(
                "{\"id\":\"::id::\"}",
                response.body,
                true
        );
    }

    @Test
    public void whenPostWithId_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        PostRequester postRequester = mock(PostRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(
                new Response("{}",200)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                postRequester,
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).post("::id::",data);
        verify(postRequester).run(new Request(
                "http://server/collection/::id::",
                "{\"name\":\"foo\"}",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenPostWithId_ShouldReturnValidResponse() throws JSONException, HttpRequesterException {
        PostRequester postRequester = mock(PostRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(
                new Response("{\"id\":\"::id::\"}",201)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        ObjectResponse response = new Collection(
                "http://server/",
                mock(GetRequester.class),
                postRequester,
                mock(PutRequester.class),
                mock(DeleteRequester.class),
                "collection"
        ).post("::id::",data);
        assertThat(response.statusCode, is(201));
        JSONAssert.assertEquals(
                "{\"id\":\"::id::\"}",
                response.body,
                true
        );
    }

    @Test
    public void whenPut_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        PutRequester putRequester = mock(PutRequester.class);
        when(putRequester.run(any(Request.class))).thenReturn(
                new Response("{}", 200)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                mock(PostRequester.class),
                putRequester,
                mock(DeleteRequester.class),
                "collection"
        ).put(data);
        verify(putRequester).run(new Request(
                "http://server/collection",
                "{\"name\":\"foo\"}",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenPutWithId_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        PutRequester putRequester = mock(PutRequester.class);
        when(putRequester.run(any(Request.class))).thenReturn(
                new Response("{}", 200)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                mock(PostRequester.class),
                putRequester,
                mock(DeleteRequester.class),
                "collection"
        ).put("::id::", data);
        verify(putRequester).run(new Request(
                "http://server/collection/::id::",
                "{\"name\":\"foo\"}",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenPutWithQuery_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        PutRequester putRequester = mock(PutRequester.class);
        when(putRequester.run(any(Request.class))).thenReturn(
                new Response("{}", 200)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        JSONObject query = new JSONObject("{\"hello\":\"world\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                mock(PostRequester.class),
                putRequester,
                mock(DeleteRequester.class),
                "collection"
        ).put(query,data);
        verify(putRequester).run(new Request(
                "http://server/collection?%7B%22hello%22%3A%22world%22%7D",
                "{\"name\":\"foo\"}",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenDeleteWithId_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        DeleteRequester deleteRequester = mock(DeleteRequester.class);
        when(deleteRequester.run(any(Request.class))).thenReturn(
                new Response("{}", 200)
        );
        JSONObject data = new JSONObject("{\"name\":\"foo\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                mock(PostRequester.class),
                mock(PutRequester.class),
                deleteRequester,
                "collection"
        ).del("::id::");
        verify(deleteRequester).run(new Request(
                "http://server/collection/::id::",
                "",
                new HashMap<String, String>()
        ));
    }

    @Test
    public void whenDeleteWithQuery_ShouldConstructValidRequest() throws JSONException, HttpRequesterException {
        DeleteRequester deleteRequester = mock(DeleteRequester.class);
        when(deleteRequester.run(any(Request.class))).thenReturn(
                new Response("{}", 200)
        );
        JSONObject query = new JSONObject("{\"hello\":\"world\"}");
        new Collection(
                "http://server/",
                mock(GetRequester.class),
                mock(PostRequester.class),
                mock(PutRequester.class),
                deleteRequester,
                "collection"
        ).del(query);
        verify(deleteRequester).run(new Request(
                "http://server/collection?%7B%22hello%22%3A%22world%22%7D",
                "",
                new HashMap<String, String>()
        ));
    }
}

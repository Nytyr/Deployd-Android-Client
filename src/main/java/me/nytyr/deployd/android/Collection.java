package me.nytyr.deployd.android;

import me.nytyr.deployd.android.requester.http.*;
import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Collection {

    private final String server;
    private final Requester getRequester;
    private final Requester postRequester;
    private final Requester putRequester;
    private final Requester deleteRequester;
    private final String collectionName;

    public Collection(
            String server,
            Requester getRequester,
            Requester postRequester,
            Requester putRequester,
            Requester deleteRequester,
            String collectionName
    ) {
        this.server = server;
        this.getRequester = getRequester;
        this.postRequester = postRequester;
        this.putRequester = putRequester;
        this.deleteRequester = deleteRequester;
        this.collectionName = collectionName;
    }

    public ArrayResponse get() throws JSONException, HttpRequesterException {
        return ArrayResponse.init(getRequester.run(buildRequester(null, null, null)));
    }

    public ObjectResponse get(String id) throws JSONException, HttpRequesterException {
        return ObjectResponse.init(getRequester.run(buildRequester(id, null, null)));
    }

    public ArrayResponse get(JSONObject query) throws JSONException, HttpRequesterException {
        return ArrayResponse.init(getRequester.run(buildRequester(null, query, null)));
    }

    public ObjectResponse post(String id, JSONObject object) throws HttpRequesterException, JSONException {
        return ObjectResponse.init(postRequester.run(
                new Request(getCollectionURL(id), object.toString())
        ));
    }

    public ObjectResponse post(JSONObject object) throws HttpRequesterException, JSONException {
        return post(null, object);
    }

    public void put(JSONObject object) throws HttpRequesterException {
        putRequester.run(buildRequester(null, null, object));
    }

    public void put(String id, JSONObject object) throws HttpRequesterException {
        putRequester.run(buildRequester(id, null, object));
    }

    public void put(JSONObject query, JSONObject object) throws HttpRequesterException {
        putRequester.run(buildRequester(null, query, object));
    }

    public void del(JSONObject query) throws HttpRequesterException {
        deleteRequester.run(buildRequester(null, query, null));
    }

    public void del(String id) throws HttpRequesterException {
        deleteRequester.run(buildRequester(id, null, null));
    }

    private Request buildRequester(String id, JSONObject query, JSONObject body) {
        String url = getCollectionURL(id);

        if (query != null) {
            try {
                url += "?" + URLEncoder.encode(query.toString(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return new Request(
                url,
                (body==null?"":body.toString())
        );
    }

    private String getCollectionURL(String id) {
        return this.server + this.collectionName  + (id != null ? "/" + id : "");
    }
}

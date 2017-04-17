package me.nytyr.deployd.android.auth;

import me.nytyr.deployd.android.auth.exception.InvalidLoginException;
import me.nytyr.deployd.android.requester.SessionRequester;
import me.nytyr.deployd.android.requester.http.Request;
import me.nytyr.deployd.android.requester.http.Response;
import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;
import org.json.JSONException;
import org.json.JSONObject;

public class Auth {

    private final String server;
    private final SessionRequester postRequester;
    private SessionStorage sessionStorage;

    public Auth(String server, SessionRequester postRequester, SessionStorage sessionStorage) {
        this.server = server;
        this.postRequester = postRequester;
        this.sessionStorage = sessionStorage;
    }

    public String login(String username, String password) throws HttpRequesterException, InvalidLoginException, JSONException {
        JSONObject loginRequest = getLoginRequestBody(username, password);
        Response response = postRequester.run(new Request(server+"users/login", loginRequest.toString()));
        if (response.statusCode != 200) {
            throw new InvalidLoginException(response.statusCode, response.body);
        }
        final JSONObject body = new JSONObject(response.body);
        String uid = body.getString("uid");
        sessionStorage.set(new Session(body.getString("id"), uid, username));
        return uid;
    }

    public void logout() throws HttpRequesterException {
        sessionStorage.set(null);
        postRequester.run(new Request(server + "users/logout", ""));
    }

    public Session getSession() {
        return sessionStorage.get();
    }

    private JSONObject getLoginRequestBody(String username, String password) throws JSONException {
        JSONObject loginRequest = new JSONObject();
        loginRequest.put("username", username);
        loginRequest.put("password", password);
        return loginRequest;
    }
}

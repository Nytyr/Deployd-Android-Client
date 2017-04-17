package me.nytyr.deployd.android.auth;

import me.nytyr.deployd.android.auth.exception.InvalidLoginException;
import me.nytyr.deployd.android.requester.SessionRequester;
import me.nytyr.deployd.android.requester.http.PostRequester;
import me.nytyr.deployd.android.requester.http.Request;
import me.nytyr.deployd.android.requester.http.Response;
import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthTest {

    @Test
    public void givenValidRequest_WhenLogin_ShouldApplyToPostRequester() throws JSONException, InvalidLoginException, HttpRequesterException {
        SessionRequester postRequester = mock(SessionRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(new Response(
                new JSONObject().put("uid", "::uid::").put("id", "::id::").toString(),
                200
        ));
        new Auth("http://server/", postRequester, mock(SessionStorage.class)).login("foo", "pass");
        verify(postRequester).run(new Request(
                "http://server/users/login",
                new JSONObject().put("username", "foo").put("password", "pass").toString(),
                new HashMap<String, String>()
        ));
    }

    @Test
    public void givenInvalidStatusCode_WhenLogin_ShouldThrowInvalidLoginException() throws JSONException, HttpRequesterException, InvalidLoginException {
        SessionRequester postRequester = mock(SessionRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(new Response(
                new JSONObject().put("message", "::error::").toString(),
                401
        ));
        boolean invalidLoginHasBeenThrown = false;
        try {
            new Auth("http://server/", postRequester, mock(SessionStorage.class)).login("foo", "pass");
        } catch (InvalidLoginException e) {
            invalidLoginHasBeenThrown = true;
            assertThat(e.getReason(), is(new JSONObject().put("message", "::error::").toString()));
            assertThat(e.getStatusCode(), is(401));
        }
        assertTrue(invalidLoginHasBeenThrown);
    }

    @Test
    public void givenValidResponse_WhenLogin_ShouldStoreSession() throws HttpRequesterException, JSONException, InvalidLoginException {
        SessionRequester postRequester = mock(SessionRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(new Response(
                new JSONObject().put("uid", "::uid::").put("id", "::id::").toString(),
                200
        ));
        SessionStorage sessionStorage = mock(SessionStorage.class);
        new Auth("http://server/", postRequester, sessionStorage).login("foo", "pass");
        verify(sessionStorage).set(new Session(
                "::id::",
                "::uid::",
                "foo"
        ));
    }

    @Test
    public void givenValidResponse_WhenLogin_ShouldReturnUserUID() throws JSONException, InvalidLoginException, HttpRequesterException {
        SessionRequester postRequester = mock(SessionRequester.class);
        when(postRequester.run(any(Request.class))).thenReturn(new Response(
                new JSONObject().put("uid", "::uid::").put("id", "::id::").toString(),
                200
        ));
        String uid = new Auth("http://server/", postRequester, mock(SessionStorage.class)).login("foo", "pass");
        assertThat(uid, is("::uid::"));
    }

    @Test
    public void whenGetSession_ShouldReturnSessionFromSessionStorage() {
        SessionStorage sessionStorage = mock(SessionStorage.class);
        when(sessionStorage.get()).thenReturn(
                new Session(
                        "::id::",
                        "::uid::",
                        "foo"
                )
        );
        Session session = new Auth("http://server/", mock(SessionRequester.class), sessionStorage).getSession();
        assertThat(session, is(new Session(
                "::id::",
                "::uid::",
                "foo"
        )));
    }

    @Test
    public void whenLogout_ShouldRemoveFromSessionStorage() throws HttpRequesterException {
        SessionStorage sessionStorage = mock(SessionStorage.class);
        new Auth("http://server/", mock(SessionRequester.class), sessionStorage).logout();
        verify(sessionStorage).set(null);
    }

    @Test
    public void whenLogout_ShouldCallRequester() throws HttpRequesterException {
        SessionRequester postRequester = mock(SessionRequester.class);
        new Auth("http://server/", postRequester, mock(SessionStorage.class)).logout();
        verify(postRequester).run(new Request(
                "http://server/users/logout",
                "",
                new HashMap<String, String>()
        ));
    }
}

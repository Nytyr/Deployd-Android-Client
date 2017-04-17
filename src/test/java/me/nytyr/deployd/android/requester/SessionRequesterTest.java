package me.nytyr.deployd.android.requester;

import me.nytyr.deployd.android.auth.Session;
import me.nytyr.deployd.android.auth.SessionStorage;
import me.nytyr.deployd.android.requester.http.Request;
import me.nytyr.deployd.android.requester.http.Requester;
import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;
import org.junit.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionRequesterTest {

    @Test
    public void GivenStoredSession_WhenRun_ShouldAddCookie() throws HttpRequesterException {
        SessionStorage sessionStorage = mock(SessionStorage.class);
        when(sessionStorage.get()).thenReturn(
                new Session(
                        "::id::",
                        "::uid::",
                        "foo"
                )
        );
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("foo", "bar");
        Requester requester = mock(Requester.class);
        new SessionRequester(requester, sessionStorage).run(new Request(
                "::server::",
                "::body::",
                headers
        ));
        verify(requester).run(new Request(
                "::server::",
                "::body::",
                new HashMap<String, String>() {{
                    put("foo", "bar");
                    put("Cookie", "sid=::id::");
                }}
        ));
    }

    @Test
    public void GivenNullStoredSession_WhenRun_ShouldNotAddCookie() throws HttpRequesterException {
        SessionStorage sessionStorage = mock(SessionStorage.class);
        when(sessionStorage.get()).thenReturn(null);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("foo", "bar");
        Requester requester = mock(Requester.class);
        new SessionRequester(requester, sessionStorage).run(new Request(
                "::server::",
                "::body::",
                headers
        ));
        verify(requester).run(new Request(
                "::server::",
                "::body::",
                new HashMap<String, String>() {{
                    put("foo", "bar");
                }}
        ));
    }
}

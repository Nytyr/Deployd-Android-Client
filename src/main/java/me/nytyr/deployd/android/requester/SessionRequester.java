package me.nytyr.deployd.android.requester;

import me.nytyr.deployd.android.auth.Session;
import me.nytyr.deployd.android.auth.SessionStorage;
import me.nytyr.deployd.android.requester.http.Request;
import me.nytyr.deployd.android.requester.http.Requester;
import me.nytyr.deployd.android.requester.http.Response;
import me.nytyr.deployd.android.requester.http.exception.HttpRequesterException;

public class SessionRequester implements Requester {

    private final Requester baseRequester;
    private final SessionStorage sessionStorage;

    public SessionRequester(Requester baseRequester, SessionStorage sessionStorage) {
        this.baseRequester = baseRequester;
        this.sessionStorage = sessionStorage;
    }

    @Override
    public Response run(Request request) throws HttpRequesterException {
        Session session = sessionStorage.get();
        if (session != null) {
            request.headers.put("Cookie", "sid="+session.sessionId);
        }
        return baseRequester.run(request);
    }
}

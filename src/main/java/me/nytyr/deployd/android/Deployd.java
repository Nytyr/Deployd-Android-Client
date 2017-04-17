package me.nytyr.deployd.android;

import android.content.Context;
import me.nytyr.deployd.android.auth.Auth;
import me.nytyr.deployd.android.auth.SessionStorage;
import me.nytyr.deployd.android.auth.SharedPreferencesSessionStorage;
import me.nytyr.deployd.android.requester.SessionRequester;
import me.nytyr.deployd.android.requester.http.*;

import java.util.HashMap;
import java.util.Map;

public class Deployd {

    private static String server;
    private static Requester getRequester;
    private static Requester postRequester;
    private static Requester putRequester;
    private static Requester deleteRequester;
    private static SessionStorage sessionStorage;
    private static SessionRequester authPostRequester;

    private static volatile Auth auth = null;
    private static Map<String, Collection> collections = new HashMap<String, Collection>();

    private Deployd() {
        //
    }

    public static void init(String server, Context context) {
        init(server, 10000, context);
    }

    public static void init(String server, int timeoutInMilliseconds, Context context) {
        SharedPreferencesSessionStorage sharedPreferencesSessionStorage = new SharedPreferencesSessionStorage(context);
        init(
                server,
                new SessionRequester(new GetRequester(timeoutInMilliseconds), sharedPreferencesSessionStorage),
                new SessionRequester(new PostRequester(timeoutInMilliseconds), sharedPreferencesSessionStorage),
                new SessionRequester(new PutRequester(timeoutInMilliseconds), sharedPreferencesSessionStorage),
                new SessionRequester(new DeleteRequester(timeoutInMilliseconds), sharedPreferencesSessionStorage),
                new SessionRequester(new PostRequester(timeoutInMilliseconds), sharedPreferencesSessionStorage),
                sharedPreferencesSessionStorage
        );
    }

    public static void init(
            String server,
            Requester getRequester,
            Requester postRequester,
            Requester putRequester,
            Requester deleteRequester,
            SessionRequester authPostRequester,
            SessionStorage sessionStorage
    ) {
        Deployd.sessionStorage = sessionStorage;
        Deployd.server = server.endsWith("/")?server:server+"/";
        Deployd.getRequester = getRequester;
        Deployd.postRequester = postRequester;
        Deployd.putRequester = putRequester;
        Deployd.authPostRequester = authPostRequester;
        Deployd.deleteRequester = deleteRequester;
    }

    public static synchronized Collection collection(String collectionName) {
        if (!collections.containsKey(collectionName)) {
            collections.put(
                    collectionName,
                    new Collection(server, getRequester, postRequester, putRequester, deleteRequester, collectionName)
            );
        }
        return collections.get(collectionName);
    }

    public static synchronized Auth auth() {
        if (auth == null) {
            synchronized(Auth.class) {
                if (auth == null)
                    auth = new Auth(server, authPostRequester, sessionStorage);
            }
        }
        return auth;
    }
}

package me.nytyr.deployd.android.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesSessionStorage implements SessionStorage {

    private final SharedPreferences sharedPreferences;
    public String sessionIdIdentifier = "session";
    public String uidIdentifier = "uid";
    public String usernameIdentifier = "username";

    public SharedPreferencesSessionStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences("DEPLOYD_SESSION_STORAGE",Context.MODE_PRIVATE);
    }

    @Override
    public Session get() {
        String sessionId = sharedPreferences.getString(sessionIdIdentifier, null);
        String uid = sharedPreferences.getString(uidIdentifier, null);
        String username = sharedPreferences.getString(usernameIdentifier, null);
        if (sessionId == null && uid == null && username == null) {
            return null;
        }
        return new Session(
                sessionId,
                uid,
                username
        );
    }

    @Override
    public void set(Session session) {
        if (session == null) {
            sharedPreferences
                    .edit()
                    .clear()
                    .commit();
            return;
        }
        sharedPreferences.edit()
                .putString(sessionIdIdentifier, session.sessionId)
                .putString(uidIdentifier, session.uid)
                .putString(usernameIdentifier, session.username)
                .commit();
    }
}

package me.nytyr.deployd.android.auth;

public interface SessionStorage {
    Session get();
    void set(Session session);
}

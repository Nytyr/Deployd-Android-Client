package me.nytyr.deployd.android.auth;

public class Session {

    public String sessionId;
    public String uid;
    public String username;

    public Session(String sessionId, String uid, String username) {
        this.sessionId = sessionId;
        this.uid = uid;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (sessionId != null ? !sessionId.equals(session.sessionId) : session.sessionId != null) return false;
        if (uid != null ? !uid.equals(session.uid) : session.uid != null) return false;
        if (username != null ? !username.equals(session.username) : session.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

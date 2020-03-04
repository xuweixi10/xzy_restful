package xzydemo.demo.Server.Session;

import javax.websocket.Session;
import java.util.ArrayList;

public class XzySession {
    protected Session session;
    protected String username;
    protected String userId;
    protected ArrayList<XzySession> XzyGroup = new ArrayList<XzySession>();

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<XzySession> getXzyGroup() {
        return XzyGroup;
    }

    public void setXzyGroup(ArrayList<XzySession> xzyGroup) {
        XzyGroup = xzyGroup;
    }
}

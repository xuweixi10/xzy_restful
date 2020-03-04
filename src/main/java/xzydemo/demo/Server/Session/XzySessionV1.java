package xzydemo.demo.Server.Session;

import javax.websocket.Session;

public class XzySessionV1 extends XzySession{

    public XzySessionV1(Session session, String username, String userId){
        this.session=session;
        this.userId=userId;
        this.username=username;
    }
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
}

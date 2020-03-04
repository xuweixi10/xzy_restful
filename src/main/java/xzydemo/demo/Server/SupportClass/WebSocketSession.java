package xzydemo.demo.Server.SupportClass;

import xzydemo.demo.Server.Session.XzySession;
import xzydemo.demo.Server.WebSocketServer;

import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketSession {
    public static String getUserNameByUserId(String userId,CopyOnWriteArrayList<XzySession> SessionSet){
        for (XzySession session:SessionSet){
            if(session.getUserId().equals(userId)){
                return session.getUsername();
            }
        }
        return null;
    }
    public static Session getSessionByUserId(String userId,CopyOnWriteArrayList<XzySession> SessionSet){
        for (XzySession session:SessionSet){
            if(session.getUserId().equals(userId)){
                return session.getSession();
            }
        }
        return null;
    }
}

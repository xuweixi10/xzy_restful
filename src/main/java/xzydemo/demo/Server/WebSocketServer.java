package xzydemo.demo.Server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xzydemo.demo.Server.Adapter.SendMessageAdapter;
import xzydemo.demo.Server.Config.XzySpringConfig;
import xzydemo.demo.Server.Session.XzySession;
import xzydemo.demo.Server.Session.XzySessionV1;
import xzydemo.demo.Server.SupportClass.WebSocketSession;
import xzydemo.demo.service.UserFriendService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws/asset/{userId}/{username}",configurator = XzySpringConfig.class)
@Component
public class WebSocketServer extends SendMessageAdapter {
    @Autowired
    UserFriendService userFriendService;

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger Online_count = new AtomicInteger(0);
    //统计在线人数
    private static CopyOnWriteArrayList<XzySession> SessionSet = new CopyOnWriteArrayList<XzySession>();
    private static MessageCacheServer messageCacheServer;

    static {
        try {
            messageCacheServer = MessageCacheServer.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void Open(Session session, @PathParam("userId") String userId,@PathParam("username") String username) {
        String username1 = userFriendService.getFriendName(userId);
        XzySession xzysession=new XzySessionV1(session, username1, userId);
        ArrayList<MessageCacheServer.Message> messages=messageCacheServer.getMessage(userId);
        SessionSet.add(xzysession);
        int count = Online_count.incrementAndGet();
        if(messages!=null&&messages.size()!=0){
            System.out.println(messages);
            for(MessageCacheServer.Message message:messages){
                SendMessage(session,message.getContent(),message.getSender(),message.getType());
            }
        }

    }
    @OnClose
    public void Close(Session session){
        XzySession xzySession = null;
        for (XzySession session1 : SessionSet) {
            if (session1.getSession()==session) {
                xzySession = session1;
            }
        }
        SessionSet.remove(xzySession);
        int count = Online_count.decrementAndGet();
        log.info("Session closed,Current online is:{}",count);
    }

    /**
     *
     * @param message [0] sendId [1] message [2] receiverId
     * @param session
     * @throws Exception
     */
    @OnMessage
    public void getMessage(String message,Session session) throws Exception {
        String[] mess =message.split(",");
        Session session1 = WebSocketSession.getSessionByUserId(mess[2],SessionSet);
        if(session1!=null){
            SendMessage(session1,mess[1],mess[0],"0");
        }
        else {
            String[] messages={mess[0],mess[1],mess[2],"0"};
            messageCacheServer.StorageMessage(messages);
        }


    }

    @OnError
    public void onError(Session session,Throwable error){
        log.error("error is:{} sessionid is:{}",error.getMessage(),session.getId());
        error.printStackTrace();
    }
    public static  void SendMessage(Session session,String text,String sendId,String type){
            try {
                String message=sendId+","+text+","+type;
                System.out.println(message);
                session.getBasicRemote().sendText(message);
                //session.getBasicRemote().sendText(String.format("%s(From %s,Session id = %s)",sendUserName,text,session.getId()));
            }catch (IOException e){
                log.error("send message Fail:{}",e.getMessage());
                e.printStackTrace();
            }
        }

    //群发消息
    public static void BroadCastMessage(String message)throws IOException {
        for (XzySession session : SessionSet) {
            if (session.getSession().isOpen()) {
                SendMessage(session.getSession(), message,"Admin");
            }
        }
    }
    public static void SendMessage(String sendUserId,String message,String userID,String type) throws Exception {
        Session session = null;
        for (XzySession s : SessionSet) {
            if(s.getUserId().equals(userID)){
                session = s.getSession();
                break;
            }
        }
        if(session!=null){
            SendMessage(session, message,sendUserId,type);
        }
        else{
            System.out.println("Message storage");
            String[] messages={sendUserId,message,userID,type};
            messageCacheServer.StorageMessage(messages);
        }
    }
    public static ArrayList<String> GetFriends(String userId){
        return null;
    }
}


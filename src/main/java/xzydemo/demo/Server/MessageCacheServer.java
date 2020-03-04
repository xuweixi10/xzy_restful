package xzydemo.demo.Server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
public class MessageCacheServer {
    private static Queue<Message> messageQueue=null;
    private static Map<String,Queue<Message>> messageMap;
    private volatile static MessageCacheServer messageCacheServer=null;


    static {
        messageMap=new ConcurrentHashMap<>();
    }

    private MessageCacheServer(){
    }
    /**
     * 该方法用于 已注入依赖的单例对象的获取
     * @return com.demo.demo.util.MsgCacheUtil
     */
    static MessageCacheServer getInstance(){
        if(messageCacheServer==null){
            synchronized (MessageCacheServer.class){
                if (messageCacheServer==null){
                    messageCacheServer=new MessageCacheServer();
                }
            }
        }
        return messageCacheServer;
    }

    /**
     * Storage message when user is offline
     * @param messageStr 通用字符数组消息
     * @exception Exception 当接收到的字符数组格式异常时抛出
     */
    public void StorageMessage(String[] messageStr) throws Exception {
        Message message= new Message(messageStr);
        System.out.println(message);
        if (!messageMap.containsKey(message.receiver)){
            messageMap.put(message.receiver,new ConcurrentLinkedQueue<>());
            messageMap.get(message.receiver).add(message);
        }
        else{
            messageMap.get(message.receiver).add(message);
        }
    }

//    /**
//     * 该方法用于将临时消息转成通用字符数组格式
//     * @param message 临时内部消息
//     * @return java.lang.String[] 通用字符数组
//     */
//    private String[] messageToStr(Message message){
//        if (message==null) {
//            return null;
//        }
//        String[] messageStr=new String[3];
//        messageStr[0]=message.sender;
//        messageStr[1]=message.content;
//        messageStr[2]=message.receiver;
//        return messageStr;
//    }

    /**
     * when user online get All messages
     * @param receiver 接受者id
     */
    public ArrayList<Message> getMessage(String receiver){
        messageQueue=messageMap.get(receiver);
        //ArrayList<Message> messages=new ArrayList<Message>();
        if (messageQueue!=null&&!messageQueue.isEmpty()){
            System.out.println(messageMap);
            messageMap.replace(receiver,new ConcurrentLinkedQueue<>());
            return new ArrayList<>(messageQueue);
        }
        System.out.println(messageMap);
        return null;
    }

    public static class Message{
        //静态内部类，临时消息容器
        final static int LENGTH = 4;

        private String sender;
        private String content;
        private String receiver;
        private String type;
        private Date date;

        public Message(String[] message) throws Exception {
            if (message==null||message.length != LENGTH){
                throw new Exception("wrong message format");
            }
            sender=message[0];
            content=message[1];
            receiver=message[2];
            type=message[3];
            date=new Date();
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReciver() {
            return receiver;
        }

        public void setReciver(String reciver) {
            this.receiver = reciver;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}

package xzydemo.demo.domain;

public class SessionData {
    /**设置session的过期时间**/
    private int interval;
    /**会话对应的 sessionid
     * 用于保证只有一个用户在线**/
    private String sessionid;
    /**会话创建时间**/
    private long creat_time;


    public SessionData(String sessionid){
        this.sessionid=sessionid;
        this.creat_time=System.currentTimeMillis();
    }
    public void setInterval(int interval){
        this.interval=interval;
    }
    public int getInterval(){
        return this.interval;
    }
    public boolean checkValid(String sessionid){
        if(System.currentTimeMillis()-this.creat_time>0){
            if(sessionid.equals(this.sessionid)) return true;
            return false;
        }
        else return false;
    }
    public String getSessionid(){
        return this.sessionid;
    }

}

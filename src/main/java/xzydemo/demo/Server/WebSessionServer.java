package xzydemo.demo.Server;

import xzydemo.demo.domain.SessionData;

import java.util.HashMap;

public class WebSessionServer {
    private static HashMap sessions=new HashMap();

    /**
     * storage user login information
     * @param uuid user uuid
     * @param sessionData user session's information;
     */
    public static void put(String uuid, SessionData sessionData){

        if(sessions.containsKey(uuid)){
            sessions.replace(uuid,sessionData);
        }
        else {
            sessions.put(uuid,sessionData);
        }

    }

    /**
     * check session's valid
     * @param uuid
     * @return
     */
    public static boolean Check_valid(String uuid,String sessionid){
        SessionData i= (SessionData) sessions.get(uuid);
        if(i!=null){
            if(i.checkValid(sessionid)){
                return true;
            }
            sessions.remove(uuid);
            return false;
        }

        return false;
    }
}

package xzydemo.demo.Server;

import java.util.UUID;

public class UuidGenerateServer {
    private volatile static UuidGenerateServer _uuidGenerator=null;
    private UuidGenerateServer(){
    }
    public static UuidGenerateServer getInstance(){
        if(_uuidGenerator==null){
            synchronized (UuidGenerateServer.class){
                if(_uuidGenerator==null){
                    _uuidGenerator=new UuidGenerateServer();
                }
            }
        }
        return _uuidGenerator;
    }
    public String GetUuid(){
        String _uuid= UUID.randomUUID().toString().replace("-","");
        return _uuid;
    }
}

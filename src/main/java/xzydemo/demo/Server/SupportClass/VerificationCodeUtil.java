package xzydemo.demo.Server.SupportClass;

import xzydemo.demo.Server.SupportClass.Interface.VerificationCodeGenerate;

import java.util.UUID;

public class VerificationCodeUtil implements VerificationCodeGenerate {
    public VerificationCodeUtil(){

    }
    public  String generateCode(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").substring(0, 6).toUpperCase();
    }
    public static String generateSessionId(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}

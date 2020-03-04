package xzydemo.demo.service;

import xzydemo.demo.domain.Account.XzyUser;

public interface XzyUserService {
    int RegisterAccount(String _uuid,String account,String password);
    String LoginResult(String account,String password);
    boolean SendVerificationCode(String MailAddress);
    boolean CheckAccount(String account,String VerificationCode);
    boolean AccountIsExist(String account);
    XzyUser getXzyUser(String _uuid);
    void InsertInformation(String username,String concatway,String sex,String userkey,String age);
}

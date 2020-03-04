package xzydemo.demo.persistence;

import xzydemo.demo.domain.Account.XzyUser;

public interface XzyUserMapper {
    void RegisterAccount(String _uuid,String account,String password);
    void InsertXzyUserInformation(String _uuid,String account,String nickname,String sex,String usersign);
    void UpdateXzyUserInformation(String nickname,String sex,String usersign);
    void InsertInformation(String username,String concatway,String sex,String userkey,String age);
    String Login(String account,String password);
    String AccountIsExist(String account);
    XzyUser getXzyUser(String account);

}

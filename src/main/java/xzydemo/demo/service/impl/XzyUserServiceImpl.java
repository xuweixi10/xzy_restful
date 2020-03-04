package xzydemo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzydemo.demo.Server.AccountCheckServer;
import xzydemo.demo.Server.SupportClass.SendMailImpl;
import xzydemo.demo.Server.SupportClass.VerificationCodeUtil;
import xzydemo.demo.domain.Account.AccountMail;
import xzydemo.demo.domain.Account.XzyUser;
import xzydemo.demo.persistence.XzyUserMapper;
import xzydemo.demo.service.XzyUserService;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class XzyUserServiceImpl implements XzyUserService {
    @Autowired
    XzyUserMapper xzyUserMapper;

    private static final AtomicInteger count = new AtomicInteger(0);
    VerificationCodeUtil verificationCodeUtil=new VerificationCodeUtil();

    @Override
    public int RegisterAccount(String _uuid, String account, String password) {
        try {
            xzyUserMapper.RegisterAccount(_uuid,account,password);
            int Count = count.incrementAndGet();
            xzyUserMapper.InsertXzyUserInformation(_uuid,account,"xzy"+Count,null,null);

            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String LoginResult(String account, String password) {
        if(xzyUserMapper.Login(account,password)!=null){
            return xzyUserMapper.Login(account,password);
        }
        return null;
    }

    @Override
    public boolean SendVerificationCode(String MailAddress) {
        AccountCheckServer accountCheckServer=AccountCheckServer.getInstance();
        String VerificationCode=verificationCodeUtil.generateCode();
        AccountMail accountMail=new AccountMail(VerificationCode,System.currentTimeMillis());
        accountCheckServer.SaveVerificationCode(MailAddress,accountMail);
        boolean res=SendMailImpl.SendMail(MailAddress,VerificationCode);
        return res;
    }

    @Override
    public boolean CheckAccount(String account, String VerificationCode) {
        AccountCheckServer accountCheckServer=AccountCheckServer.getInstance();
        return accountCheckServer.CheckValid(account,VerificationCode);
    }

    @Override
    public boolean AccountIsExist(String account) {
        return null != xzyUserMapper.AccountIsExist(account);
    }

    @Override
    public XzyUser getXzyUser(String account) {
        return xzyUserMapper.getXzyUser(account);
    }

    @Override
    public void InsertInformation(String username,String concatway,String sex,String userkey,String age){
        xzyUserMapper.InsertInformation(username,concatway,sex, userkey,age);
    }
}

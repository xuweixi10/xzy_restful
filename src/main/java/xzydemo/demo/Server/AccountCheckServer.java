package xzydemo.demo.Server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xzydemo.demo.Server.SupportClass.Interface.AccountCheck;
import xzydemo.demo.domain.Account.AccountMail;

import java.util.HashMap;
import java.util.Iterator;

public class AccountCheckServer implements AccountCheck {
    private HashMap account_VerificationCode=new HashMap();
    private static volatile AccountCheckServer accountCheckServer;
    private AccountCheckServer(){

    }
    public static AccountCheckServer getInstance(){
        if(accountCheckServer==null){
            synchronized (AccountCheckServer.class){
                if(accountCheckServer==null){
                    accountCheckServer=new AccountCheckServer();
                }
            }
        }
        return accountCheckServer;
    }
    @Override
    public boolean CheckValid(String account, String code) {
        AccountMail accountMail= (AccountMail) this.account_VerificationCode.get(account);
        if(accountMail!=null&&accountMail.getMailVerificationCode().equals(code)){
            this.account_VerificationCode.remove(account);
            return true;
        }
        return false;
    }

    /**
     * Save the account and verification
     * @param account user mail account
     * @param accountMail the class contain code and code create_time
     */
    public void SaveVerificationCode(String account, AccountMail accountMail){
        if(this.account_VerificationCode.containsKey(account)){
            this.account_VerificationCode.replace(account,accountMail);
        }
        else {
            this.account_VerificationCode.put(account,accountMail);
        }

    }
    public void CheckTime(){
        if(this.account_VerificationCode!=null){
            Iterator it = account_VerificationCode.keySet().iterator();
            if(it.hasNext()){
                String key = (String) it.next();
                AccountMail accountMail= (AccountMail) account_VerificationCode.get(key);
                long current_time=System.currentTimeMillis()/1000;
                if(current_time-accountMail.getCreatTime()>1800){
                    account_VerificationCode.remove(key);
                }
            }
        }
    }
}

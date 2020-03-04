package xzydemo.demo.domain.Account;

public class AccountMail {
    private String MailVerificationCode;
    private long CreatTime;
    public AccountMail(String mailVerificationCode,long creatTime){
        this.MailVerificationCode=mailVerificationCode;
        this.CreatTime=creatTime;
    }
    public String getMailVerificationCode() {
        return MailVerificationCode;
    }

    public void setMailVerificationCode(String mailVerificationCode) {
        MailVerificationCode = mailVerificationCode;
    }

    public long getCreatTime() {
        return CreatTime;
    }

    public void setCreatTime(long creatTime) {
        CreatTime = creatTime;
    }
}

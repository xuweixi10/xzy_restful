package xzydemo.demo.Server.SupportClass.Interface;

public interface AccountCheck {
    /**
     *
     * @param account user Account  Verification
     * @param code user insert VerificationCode
     * @return
     */
    boolean CheckValid(String account,String code);
}

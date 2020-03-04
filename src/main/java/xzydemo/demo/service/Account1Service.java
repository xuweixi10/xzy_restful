package xzydemo.demo.service;

import xzydemo.demo.domain.Account1DOWithBLOBs;

import java.util.List;

/**
 * @Description
 * @Author yhk
 * @Date 2019/10/20 16:13
 */
public interface Account1Service {

    public void removeAccountInfo(String accountId);

    public Account1DOWithBLOBs addNewAccount(Account1DOWithBLOBs account1DOWithBLOBs);

    public Account1DOWithBLOBs updateAccountInfo(Account1DOWithBLOBs account1DOWithBLOBs);

    public List<Account1DOWithBLOBs> selectWithCondition(List<String> province,Boolean has_married,Integer maxAge ,Integer minAge);

    public Account1DOWithBLOBs selectByAccountID(String accountId) throws Exception;


}

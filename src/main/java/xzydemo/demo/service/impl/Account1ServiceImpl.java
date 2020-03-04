package xzydemo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzydemo.demo.domain.Account1DOWithBLOBs;
import xzydemo.demo.persistence.Account1DOMapper;
import xzydemo.demo.service.Account1Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author yhk
 * @Date 2019/10/20 17:11
 */
@Service
public class Account1ServiceImpl implements Account1Service {

    @Autowired
    Account1DOMapper account1DOMapper;

    @Override
    public void removeAccountInfo(String accountId) {
        account1DOMapper.deleteByPrimaryKey(accountId);
    }

    @Override
    public Account1DOWithBLOBs addNewAccount(Account1DOWithBLOBs account1DOWithBLOBs) {
        String accountId=account1DOWithBLOBs.getAccountid();
        account1DOMapper.insertSelective(account1DOWithBLOBs);
        return account1DOMapper.selectByPrimaryKey(accountId);

    }

    @Override
    public Account1DOWithBLOBs updateAccountInfo(Account1DOWithBLOBs account1DOWithBLOBs) {
        String accountId=account1DOWithBLOBs.getAccountid();
        account1DOMapper.updateByPrimaryKeySelective(account1DOWithBLOBs);
        return account1DOMapper.selectByPrimaryKey(accountId);
    }


    /*
    该方法逻辑混乱，需要重构
    */
    @Override
    public List<Account1DOWithBLOBs> selectWithCondition(List<String> province, Boolean has_married, Integer maxAge, Integer minAge) {
        List<Account1DOWithBLOBs> accountList1=null,accountList2=null,accountList3=null,accountList;
        System.out.println(province.toString()+":"+has_married+":"+maxAge+":"+minAge);
        if (province!=null&&!province.isEmpty()){
            accountList1=account1DOMapper.selectWithConditionByProvince(province);
        }
        if (has_married !=null){
            accountList2=account1DOMapper.selectWithConditionByHas_married(has_married?1:0);
        }
        if (maxAge !=null || minAge !=null){
            maxAge=maxAge==null?80:maxAge;
            minAge=minAge==null?22:minAge;
            accountList3=account1DOMapper.selectWithConditionByAge(minAge,maxAge);
        }
        if (accountList1!=null){
            accountList=new ArrayList<>(accountList1);
            if (accountList2!=null){
                accountList.retainAll(accountList2);
                if (accountList3!=null){
                    accountList.retainAll(accountList3);
                }
            }
            else if (accountList3!=null){
                accountList.retainAll(accountList3);
            }
        }
        else if (accountList2!=null){
            accountList=new ArrayList<>(accountList2);
            if (accountList3!=null) {
                accountList.retainAll(accountList3);
            }
        }
        else if(accountList3!=null){
            accountList=new ArrayList<>(accountList3);
        }
        else {
            accountList=account1DOMapper.selectAll();
        }
        return accountList;
    }

    @Override
    public Account1DOWithBLOBs selectByAccountID(String accountId) throws Exception {
        Account1DOWithBLOBs account1DOWithBLOBs = account1DOMapper.selectByPrimaryKey(accountId);
        return account1DOWithBLOBs;
    }
}

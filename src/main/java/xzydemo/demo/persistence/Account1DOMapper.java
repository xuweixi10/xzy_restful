package xzydemo.demo.persistence;

import org.apache.ibatis.annotations.Param;
import xzydemo.demo.domain.Account1DO;
import xzydemo.demo.domain.Account1DOWithBLOBs;

import java.util.List;

public interface Account1DOMapper {

    int deleteByPrimaryKey(String accountid);


    int insert(Account1DOWithBLOBs record);


    int insertSelective(Account1DOWithBLOBs record);


    Account1DOWithBLOBs selectByPrimaryKey(String accountid);


    int updateByPrimaryKeySelective(Account1DOWithBLOBs record);


    int updateByPrimaryKeyWithBLOBs(Account1DOWithBLOBs record);


    int updateByPrimaryKey(Account1DO record);


    List<Account1DOWithBLOBs> selectWithConditionByProvince(List<String> province);


    List<Account1DOWithBLOBs> selectWithConditionByHas_married(int hasMarried);

    List<Account1DOWithBLOBs> selectWithConditionByAge(@Param("min") int min, @Param("max")int max);

    List<Account1DOWithBLOBs> selectAll();
}
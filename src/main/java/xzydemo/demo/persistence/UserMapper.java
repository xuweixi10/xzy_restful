package xzydemo.demo.persistence;

import xzydemo.demo.domain.User;

import java.util.List;

public interface UserMapper {
    void inseruser_class(User user);//预约课程
    void deleteuser_class(User user);//取消预约
    void deleteClass_user(String Class_id);//删除已结束课程
    List<User> getusersfromclass(String Class_id);//获取某一课程的所有人信息
    User finduserinclass(String classid,String useropenid);//查询是否预约课程
}

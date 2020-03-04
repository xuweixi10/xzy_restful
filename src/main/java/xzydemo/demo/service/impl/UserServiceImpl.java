package xzydemo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.User;
import xzydemo.demo.persistence.ClassMapper;
import xzydemo.demo.persistence.UserMapper;
import xzydemo.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public void Insert_UserClass(User user) {
        userMapper.inseruser_class(user);
        classMapper.ReduceClasspeople(user.getClassid());
    }

    @Override
    public Class GetClass(String username) {
        return null;
    }//暂无使用

    @Override
    public void deleteUserClass(String classname) {
        //暂无使用
    }

    @Override
    public String findUserClass(String classid,String useropenid) {
        User user =userMapper.finduserinclass(classid,useropenid);
        if(user==null){
            return null;
        }
        else {
            return user.getName();
        }
    }
}

package xzydemo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.User;
import xzydemo.demo.persistence.ClassMapper;
import xzydemo.demo.persistence.UserMapper;
import xzydemo.demo.service.ManagerService;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private  ClassMapper classMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void deleteClass(String Class_id) {
        userMapper.deleteClass_user(Class_id);
        classMapper.DeleteClass(Class_id);
    }

    @Override
    public void InsertClass(Class class_details) {
        classMapper.InsertClass(class_details);
    }

    @Override
    public List<User> getUsers(String Class_id) {
        return userMapper.getusersfromclass(Class_id);
    }
}

package xzydemo.demo.service;

import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.User;

public interface UserService {
    public void Insert_UserClass(User user);
    public Class GetClass(String username);
    public void deleteUserClass(String classname);
    String findUserClass(String useropenid,String classid);
}

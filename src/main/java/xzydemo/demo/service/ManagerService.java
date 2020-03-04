package xzydemo.demo.service;

import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.User;

import java.util.List;

public interface ManagerService {
    public void deleteClass(String Class_id);
    public void InsertClass(Class class_details);
    public List<User> getUsers(String Class_id);
}

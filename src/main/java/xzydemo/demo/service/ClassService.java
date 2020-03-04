package xzydemo.demo.service;

import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.ClassInfor;

import java.util.List;

public interface ClassService {
    List<ClassInfor> getClassInfor();
    Class getClassDetails(String Class_id);
}

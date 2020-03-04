package xzydemo.demo.persistence;

import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.ClassInfor;

import java.util.List;

public interface ClassMapper {
    List<ClassInfor> getClassInfor();//获取所有课程概要信息
    Class getClassDetails(String Class_id);//获取课程详细信息
    void InsertClass(Class ClassDetails);//插入课程
    void DeleteClass(String Class_id);//删除课程
    void ReduceClasspeople(String classid);//减少人数

}

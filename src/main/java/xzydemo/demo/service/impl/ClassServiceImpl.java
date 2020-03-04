package xzydemo.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.ClassInfor;
import xzydemo.demo.persistence.ClassMapper;
import xzydemo.demo.service.ClassService;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public List<ClassInfor> getClassInfor() {//
        return classMapper.getClassInfor();
    }

    @Override
    public Class getClassDetails(String Class_id) {

        return classMapper.getClassDetails(Class_id);
    }


}

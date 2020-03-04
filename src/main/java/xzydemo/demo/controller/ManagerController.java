package xzydemo.demo.controller;

//import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.User;
import xzydemo.demo.service.ManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping("/INSERT_CLASS")
    public int INSERT_CLASS(@RequestBody JSONObject jsonObject,
                            HttpServletRequest request, HttpServletResponse response){
        Class Class_details=new Class();
        Class_details.setClass_id(jsonObject.getAsString("class_id"));
        Class_details.setClass_name(jsonObject.getAsString("classname"));
        Class_details.setPresenter(jsonObject.getAsString("Presenter"));
        Class_details.setClass_time(jsonObject.getAsString("Class_time"));
        Class_details.setClass_address(jsonObject.getAsString("Class_address"));
        Class_details.setPeople(jsonObject.getAsString("people"));
        Class_details.setDescn(jsonObject.getAsString("descn"));
        Class_details.setDescn2(jsonObject.getAsString("descn2"));
        Class_details.setDescn3(jsonObject.getAsString("descn3"));
        try {
            managerService.InsertClass(Class_details);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    @RequestMapping("/DELETE_CLASS/{class_id}")
    public void DELETE_CLASS(@PathVariable("class_id") String class_id){
        managerService.deleteClass(class_id);
    }
    @RequestMapping("/GET_USER/{class_id}")
    public List<User> GET_USERS(@PathVariable("class_id") String class_id){
        return managerService.getUsers(class_id);
    }
}

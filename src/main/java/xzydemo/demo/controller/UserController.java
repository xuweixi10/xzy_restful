package xzydemo.demo.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xzydemo.demo.domain.Class;
import xzydemo.demo.domain.ClassInfor;
import xzydemo.demo.domain.User;
import xzydemo.demo.service.ClassService;
import xzydemo.demo.service.CodeToOpenID;
import xzydemo.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private ClassService classService;
    @Autowired
    private CodeToOpenID codeToOpenID;
    @Autowired
    private UserService userService;
    @GetMapping("/Class/getClassInfor/{Class_id}")
    public List<ClassInfor> getClassInfor(@PathVariable("Class_id") String Class_id){
        return classService.getClassInfor();
    }
    @GetMapping("/Class/getClassDetails/{Class_id}")
    public Class getClassDetails(@PathVariable("Class_id") String Class_id){
        return classService.getClassDetails(Class_id);
    }
    @GetMapping("/getOpenId/{sessioncode}")
    public  String getOpenId(@PathVariable("sessioncode") String code) {

        return codeToOpenID.GetOpenID(code);
    }
    @PostMapping("/Class/InsertClassUser")
    public int InsertClassUser(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request, HttpServletResponse response){
        User user=new User();
        user.setName(jsonObject.getAsString("name"));
        user.setClassid(jsonObject.getAsString("class_id"));
        user.setUseropenid(jsonObject.getAsString("open_id"));
        userService.Insert_UserClass(user);
        return 0;
    }
    @GetMapping("/Class/UserhadClass")
    public String FindUser_Class(@RequestParam("useropenid") String useropenid,
                                 @RequestParam("classid") String classid){
        return userService.findUserClass(classid,useropenid);
    }
}

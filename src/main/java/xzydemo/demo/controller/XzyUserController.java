package xzydemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xzydemo.demo.Server.SupportClass.VerificationCodeUtil;
import xzydemo.demo.Server.UuidGenerateServer;
import xzydemo.demo.Server.WebSessionServer;
import xzydemo.demo.domain.SessionData;
import xzydemo.demo.service.XzyUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@CrossOrigin
@Controller
@RequestMapping("/XzyAccount")
public class XzyUserController {
    @Autowired
    XzyUserService xzyUserService;

    @ResponseBody
    @RequestMapping(value = "/CheckMail",method = {RequestMethod.POST})
    public int CheckMailValid(@RequestParam("Mail") String MailAccount){
        if(!xzyUserService.AccountIsExist(MailAccount)){
            try {
                if(xzyUserService.SendVerificationCode(MailAccount)){
                    return 0;
                }
                return 1;
            }catch (Exception e){
                e.printStackTrace();
                return 2;
            }
        }
        return 3;
    }
    @ResponseBody
    @RequestMapping(value = "/Register/UserInfor",method = {RequestMethod.POST})
    public int Register(
            @RequestParam("Account") String account,
            @RequestParam("Password") String password,
            @RequestParam("VerificationCode" ) String code){
        if(xzyUserService.CheckAccount(account,code)){
            UuidGenerateServer uuidGenerateServer=UuidGenerateServer.getInstance();
            String _uuid=uuidGenerateServer.GetUuid();
            try {
                xzyUserService.RegisterAccount(_uuid,account,password);
                return 1;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @ResponseBody
    @RequestMapping(value = "/Login/Session",method = {RequestMethod.GET})
    public String[] Login(
            @RequestParam("Account") String account,
            @RequestParam("Password") String password,
            HttpServletRequest request){

        String _uuid=xzyUserService.LoginResult(account,password);
        if(_uuid!=null){
            String code=VerificationCodeUtil.generateSessionId();
            SessionData sessionData=new SessionData(code);
            sessionData.setInterval(1800*1000);
            WebSessionServer.put(_uuid,sessionData);
            String data[]={_uuid,code};
            return data;
        }
        else {
            String data[]={"0","0"};
            return data;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/Status",method = {RequestMethod.GET})
    public boolean GetStatus(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        Cookie cookies[]=request.getCookies();
        String access_token=null;
        String uuid=null;
        if(null!=cookies){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("access_token")){
                    access_token=cookie.getValue();
                }
                if(cookie.getName().equals("uuid")){
                    uuid=cookie.getValue();
                }
            }
            if(access_token!=null&&uuid!=null){
                return WebSessionServer.Check_valid(uuid,access_token);
            }
            return false;
        }
        return false;
    }

}

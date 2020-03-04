package xzydemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xzydemo.demo.Server.WebSocketServer;
import xzydemo.demo.domain.Account.XzyUser;
import xzydemo.demo.service.UserFriendService;
import xzydemo.demo.service.XzyUserService;

import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping("/friend")
public class UserFriendController {
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    XzyUserService xzyUserService;


    /**
     *
     * @param account user _uuid
     * @param friendAccount friend _uuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/send/friendRequest",method = {RequestMethod.POST})
    public boolean sendFriendRequest(@RequestParam("account") String account,
                                     @RequestParam("friendAccount") String friendAccount){
        try {
            WebSocketServer.SendMessage(account,"friend",friendAccount,"1");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/send/refuseFriendRequest",method = {RequestMethod.POST})
    public boolean refuseFriendRequest(@RequestParam("account") String account,
                                     @RequestParam("friendAccount") String friendAccount){
        try {
            WebSocketServer.SendMessage(account,"refuseFriend",friendAccount,"1");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     *
     * @param account user _uuid
     * @param friendAccount friend _uuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add/friendAccount",method = {RequestMethod.POST})
    public boolean addFriend(@RequestParam("account") String account,
                             @RequestParam("friendAccount") String friendAccount){
        try {
            WebSocketServer.SendMessage(account,"receiveFriend",friendAccount,"1");
            userFriendService.addFriend(account,friendAccount);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param account
     * @param friendAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/friendAccount",method = {RequestMethod.POST})
    public boolean deleteFriend(@RequestParam("account") String account,
                                @RequestParam("friendAccount") String friendAccount){
        try {
            userFriendService.deleteFriend(account,friendAccount);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    @ResponseBody
    @RequestMapping(value = "/friendsList",method = {RequestMethod.GET})
    public ArrayList<String> getFriends(@RequestParam("account") String account){
        try {
            return userFriendService.getFriends(account);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * @param friendAccount friend _uuid
     * @return friend NickName default xzyUser
     */
    @ResponseBody
    @RequestMapping(value = "/userNickName",method = {RequestMethod.GET})
    public String getUserNickName(@RequestParam("friendAccount") String friendAccount){
        return userFriendService.getFriendName(friendAccount);

    }

    @ResponseBody
    @RequestMapping(value = "/searchUserInformation",method = {RequestMethod.GET})
    public XzyUser searchUser(@RequestParam("searchAccount") String account){
            return xzyUserService.getXzyUser(account);
    }
}

package xzydemo.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xzydemo.demo.Server.WebSocketServer;

import java.io.IOException;

@RestController
@RequestMapping("/api/ws")
public class WebSocketController {

    @RequestMapping(value="/sendAll", method= RequestMethod.GET)
    /**
     * 群发消息内容
     * @param message
     * @return
     */
    String sendAllMessage(@RequestParam(required=true) String message){
        try {
            WebSocketServer.BroadCastMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
    @RequestMapping(value="/sendOne", method=RequestMethod.GET)
    /**
     * 指定会话ID发消息
     * @param message 消息内容
     * @param id 连接会话ID
     * @return
     */
    String sendOneMessage(@RequestParam("message") String message,
                          @RequestParam("userId") String id,
                          @RequestParam("sendUserId") String SendUserId){
        try {
            WebSocketServer.SendMessage(id,message,SendUserId,"1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}


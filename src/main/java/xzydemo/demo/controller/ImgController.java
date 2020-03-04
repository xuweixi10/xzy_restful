package xzydemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xzydemo.demo.Server.ImgUpload;
import xzydemo.demo.Server.UuidGenerateServer;
import xzydemo.demo.domain.Account1DOWithBLOBs;
import xzydemo.demo.service.XzyUserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/upload")
public class ImgController {
    @Autowired
    XzyUserService xzyUserService;

    @ResponseBody
    @RequestMapping(value = "/uploadImage",method = {RequestMethod.POST})
    public boolean uploadImage(@RequestParam MultipartFile myFile,
                            @RequestParam("username") String username,
                            @RequestParam("concatway") String concatway,
                            @RequestParam("sex") String sex,
                            @RequestParam("age") String age) throws Exception {
        if(!myFile.isEmpty()){
            try {
                UuidGenerateServer uuidGenerateServer= UuidGenerateServer.getInstance();
                String userkey=uuidGenerateServer.GetUuid();
                xzyUserService.InsertInformation(username,concatway,sex,userkey,age);
                ImgUpload.upload(myFile,userkey);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}

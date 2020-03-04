package xzydemo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xzydemo.demo.domain.Account1DOWithBLOBs;
import xzydemo.demo.service.Account1Service;
import xzydemo.demo.service.CodeToOpenID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Description
 * @Author yhk
 * @Date 2019/10/13 16:10
 */

@CrossOrigin
@Controller
@RequestMapping("/account")
public class Account1Controller {
    @Autowired
    private Account1Service accountService;
    /**
     * @name 添加用户方法
     * @param accountId 需要添加的用户的id 字符串，长度小于32
     * @param name 用户姓名 字符串， 长度小于20
     * @param age  用户年龄 字符串， 22-80范围，要求前端校验后
     * @param has_married 是否有过婚史，整数， 0=无，1=有，不容许有其他数值，要求前端校验
     * @param province 用户所在省份，字符串，长度小于20，建议使用枚举量，要求与数据库内容统一
     * @param addr 用户所在详细位置，大值城市小至小区，字符串，长度小于64，要求前端校验安全性防止sql注入
     * @param self_introduce 自我介绍，长文本，长度任意，大小小于65kbyte
     * @param require 需求，长文本，长度任意，大小小于65kbyte
     * @return 返回新增的账户
     * @decribe 添加新的用户信息（注图片需要单独增加）
     */
    @ResponseBody
    @RequestMapping(value = "/addNewAccount",method={RequestMethod.POST})
    public Account1DOWithBLOBs addNewAccount(@RequestParam("accountid") String accountId,
                                             @RequestParam("name") String name,
                                             @RequestParam("age") Integer age,
                                             @RequestParam("has_married") int has_married,
                                             @RequestParam("province") String province,
                                             @RequestParam("addr") String addr,
                                             @RequestParam("self_introduce") String self_introduce,
                                             @RequestParam("require")String require) throws Exception {
        if (accountId==null) {
            throw new Exception("accountId is null");
        }
        Account1DOWithBLOBs account=accountService.selectByAccountID(accountId);
        if (account!=null){
            throw new Exception("account exists");
        }

        account=new Account1DOWithBLOBs();
        account.setAccountid(accountId);
        account.setRealname(name);
        account.setAge(age);
        account.setHasMarried((byte) (has_married==0?0:1));
        account.setProvince(province);
        account.setAddr(addr);
        account.setSelfIntroduce(self_introduce);
        account.setRequirement(require);
        return accountService.addNewAccount(account);
    }

    /**
     * @name 更新用户信息方法
     * @param accountId 需要更新的用户的id 字符串，长度小于32
     * @param name 用户姓名 字符串， 长度小于20
     * @param age  用户年龄 字符串， 22-80范围，要求前端校验后
     * @param has_married 是否有过婚史，整数， 0=无，1=有，不容许有其他数值，要求前端校验
     * @param province 用户所在省份，字符串，长度小于20，建议使用枚举量，要求与数据库内容统一
     * @param addr 用户所在详细位置，大值城市小至小区，字符串，长度小于64，要求前端校验安全性防止sql注入
     * @param self_introduce 自我介绍，长文本，长度任意，大小小于65kbyte
     * @param require 需求，长文本，长度任意，大小小于65kbyte
     * @return 返回更新后的账户
     * @decribe 返回更新后的账户信息（注意，图片需要单独更新）
     */
    @ResponseBody
    @RequestMapping(value = "/updateAccountInfo",method={RequestMethod.POST})
    public Account1DOWithBLOBs updateAccountInfo(@RequestParam("accountid") String accountId,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("age") Integer age,
                                                 @RequestParam("has_married") int has_married,
                                                 @RequestParam("province") String province,
                                                 @RequestParam("addr") String addr,
                                                 @RequestParam("self_introduce") String self_introduce,
                                                 @RequestParam("require")String require) throws Exception {
        if (accountId==null) {
            throw new Exception("accountId is null");
        }
        Account1DOWithBLOBs account=new Account1DOWithBLOBs();
        account.setAccountid(accountId);
        account.setRealname(name);
        account.setAge(age);
        account.setHasMarried((byte) (has_married==0?0:1));
        account.setProvince(province);
        account.setAddr(addr);
        account.setSelfIntroduce(self_introduce);
        account.setRequirement(require);
        return accountService.updateAccountInfo(account);
    }

    /**
     * @name 返回用户信息
     * @param accountId 要查询的用户
     * @decribe 这个方法用于展示所给accountid的account信息
     */
    @ResponseBody
    @RequestMapping(value = "/getAccount",method = {RequestMethod.GET})
    public Account1DOWithBLOBs getAccount(@RequestParam("accountId")String accountId) throws Exception {
        if (accountId==null) {
            throw new Exception("accountId is null");
        }
        if(accountService.selectByAccountID(accountId)==null){
            return null;
        }
        return accountService.selectByAccountID(accountId);
    }

    /**
     * @name 按条件搜索
     * @param provinces 需要查询的省份，要求前端传入一个字符串数组 data: {"yourParam[]":[1,2,3,4]}，且内容要与数据库保持一致，
     *                  若未设置该条件，则要传入一个空值
     * @param has_married 是否有过婚史，有=1，无=0，若未设置该条件，则要传入一个空值
     * @param minAge 设定查询的最小年龄，若未设置该条件，则要传入一个空值
     * @param maxAge 设定查询的最大年龄，若未设置该条件，则要传入一个空值
     * @decribe 这个方法用于根据所给条件查询出必要的内容
     * @require 如果未设置查询条件，就将查询条件置空
     */
    @ResponseBody
    @RequestMapping(value = "/selectByCondition",method ={RequestMethod.GET})
    public List<Account1DOWithBLOBs> selectByCondition(@RequestParam("provinces")String[] provinces,
                                                       @RequestParam("has_married") Integer has_married,
                                                       @RequestParam("minAge")Integer minAge,
                                                       @RequestParam("maxAge")Integer maxAge){
        return accountService.selectWithCondition(Arrays.asList(provinces),(has_married==null?null:(has_married==0?false:true)),maxAge,minAge);
    }

    /**
     * @name 用户删除方法
     * @param accountId 需要删除的id 字符串，长度小于32
     * @return 返回更新/新增过图片的账户
     * @decribe 删除目标id的account
     */
    @ResponseBody
    @RequestMapping(value ="/deleteAccount",method = {RequestMethod.POST})
    public String deleteAccount(@RequestParam("accountId") String accountId) throws Exception {
        if (accountId==null) {
            throw new Exception("accountId is null");
        }
        accountService.removeAccountInfo(accountId);
        return "success";
    }

    /**
     * @name 图片上传方法
     * @param request 当前用户的请求
     * @param myFile 用户上传的文件
     * @return 返回更新/新增过图片的账户
     * @decribe 这个方法仅用于上传账户图片，只能选定一张，目前尚未能判断图片类型，仅支持jpeg格式；上传其他类型的图片可能会出现编码错误
     * @require 需要在请求头（Header）中添加一条   "AccountId":*******  的标识记录，用于验证身份
     */
    @ResponseBody
    @RequestMapping(value = "/uploadImage",method = {RequestMethod.POST})
    public Account1DOWithBLOBs uploadImage(@RequestParam MultipartFile myFile, HttpServletRequest request) throws Exception {
        String accountId = request.getHeader("AccountId");
        if (accountId==null) {
            throw new Exception("accountId is null");
        }
        Account1DOWithBLOBs account=accountService.selectByAccountID(accountId);

        if(!myFile.isEmpty()){

            try {
                byte[] image = myFile.getBytes();
                if (account==null){
                    account=new Account1DOWithBLOBs();
                    account.setAccountid(accountId);
                    account.setPicture(image);
                    accountService.addNewAccount(account);
                }
                else {
                    account.setPicture(image);
                    accountService.updateAccountInfo(account);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return account;
    }


    /**
     * @name 图片展示方法
     * @param request 当前用户的请求
     * @param response 包含用于展示图片的流的相应
     * @decribe 这个方法仅用于展示账户图片，目前仅支持jpeg格式；其他类型的图片可能会出现编码错误
     * @require 需要在请求头（Header）中添加一条   "AccountId":*******  的标识记录，用于验证身份
     */
    @RequestMapping(value = "getAccountImg",method = RequestMethod.GET)
    public void showImage(@RequestParam("AccountId")String accountId, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (accountId==null) {
            throw new Exception("accountId is null");
        }
        Account1DOWithBLOBs account=accountService.selectByAccountID(accountId);
        byte[] byteAry = account.getPicture();
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        out.write(byteAry);
        out.flush();
        out.close();
    }


}

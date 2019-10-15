package com.yingxs.security.controller;

import com.yingxs.security.aop.SysLog;
import com.yingxs.security.support.SimpleResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/user")
@RestController
public class UserController {


    @RequestMapping(value = "/test")
    public String test(){

        int i = 1/0;
        return "success";
    }


    /**
     * @author yingxs
     * @date 2019-10-15 10:31:43
     */
    @SysLog("获取当前登录用户")
    @RequestMapping(value = "/me")
    public SimpleResponse me(@AuthenticationPrincipal UserDetails user){
        return new SimpleResponse("success",user);
    }



}

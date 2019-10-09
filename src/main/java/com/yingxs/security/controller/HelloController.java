package com.yingxs.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/hello")
@RestController
public class HelloController {


    @RequestMapping(value = "/test")
    public String test(){

        int i = 1/0;
        return "success";
    }



}

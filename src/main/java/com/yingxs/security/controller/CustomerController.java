package com.yingxs.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {


    @GetMapping("/customer")
    public String customer(){
        return "get customer";
    }
    @PostMapping("/customer")
    public String postcustomer(){
        return "post customer";
    }
    @PutMapping("/customer")
    public String putcustomer(){
        return "put customer";
    }

}

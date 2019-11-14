package com.yingxs.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yingxs.security.aop.SysLog;
import com.yingxs.security.support.SimpleResponse;
import com.yingxs.security.validate.ImageCode;
import com.yingxs.security.validate.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE_";

    @Autowired
    private ValidateCodeGenerator validateCodeGenerator;

//    @SysLog("获取图形验证码")
//    @GetMapping("/code/image")
//    public SimpleResponse createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ImageCode imageCode = validateCodeGenerator.generate( new ServletWebRequest(request) );
//        HttpSession session = request.getSession();
//        session.setAttribute(SESSION_KEY+imageCode.getCodeId(), imageCode);
//        return new SimpleResponse("SUCCESS",imageCode);
////      ImageIO.write(imageCode.getImage(), "PNG", response.getOutputStream());
//    }

//    @SysLog("获取图形验证码")
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ImageCode imageCode = validateCodeGenerator.generate( new ServletWebRequest(request) );
//        HttpSession session = request.getSession();
//        session.setAttribute(SESSION_KEY+imageCode.getCodeId(), imageCode);
//        return new SimpleResponse("SUCCESS",imageCode);
        ImageIO.write(imageCode.getImage(), "PNG", response.getOutputStream());
    }

}

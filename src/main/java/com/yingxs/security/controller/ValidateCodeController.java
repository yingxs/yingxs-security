package com.yingxs.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingxs.security.aop.SysLog;
import com.yingxs.security.properties.SecurityProperties;
import com.yingxs.security.support.SimpleResponse;
import com.yingxs.security.validate.ImageCode;
import com.yingxs.security.validate.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private ValidateCodeGenerator validateCodeGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;


    @SysLog("获取图形验证码")
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (securityProperties.getCode().getImage().isEnable()) {
            ImageCode imageCode = validateCodeGenerator.generate( new ServletWebRequest(request) );
            HttpSession session = request.getSession();
            session.setAttribute(SESSION_KEY, imageCode);
            ImageIO.write(imageCode.getImage(), "PNG", response.getOutputStream());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("验证码未开启")));
        }

    }


}

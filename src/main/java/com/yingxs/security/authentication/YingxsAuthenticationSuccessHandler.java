package com.yingxs.security.authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingxs.security.aop.SysLog;
import com.yingxs.security.support.UserInfo;
import com.yingxs.security.support.UserInfo2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录成功处理器
 * @author yingxs
 * @date 2019-10-8 09:01:34
 * @email ying_xs@163.com
 */
@Component("yingxsAuthenticationSuccessHandler")
@Slf4j
public class YingxsAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @SysLog("登录成功处理")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info( ((UserInfo)authentication.getPrincipal()).getUsername()+ " -> 登录成功" );
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authentication.getPrincipal()));
    }


}

package com.yingxs.security.authentication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yingxs.security.aop.SysLog;
import com.yingxs.security.support.UserInfo;
import com.yingxs.security.support.UserInfo2;
import com.yingxs.security.utils.ContextUtil;
import com.yingxs.security.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


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

    @SysLog("用户登录")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        String params = "";
//        try {
//            params = objectMapper.writeValueAsString(request.getParameterMap());
//        } catch (JsonProcessingException e) {}
//        String requestUri = request.getRequestURI();
//        String method = request.getMethod();
//        String loginIp = ContextUtil.getIpAddr(request);
//        log.info("type----> {}", "info");
//        log.info("requestUri----> {}", requestUri);
//        log.info("params----> {}", params);
//        log.info("method----> {}", method);
//        log.info("action----> {}", "用户登录");
//        log.info("userName----> {}", ((UserInfo)authentication.getPrincipal()).getUsername() );
//        log.info("browser----> {}", WebUtil.getBrowserName(request));
//        log.info("loginIp----> {}", loginIp);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authentication.getPrincipal()));
    }


}

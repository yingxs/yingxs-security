package com.yingxs.security.validate;

import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * 验证码生成逻辑接口
 * @author yingxs
 * @date 2019-10-18 11:31:07
 */
public interface ValidateCodeGenerator {

    ImageCode generate(ServletWebRequest request) throws IOException;

}

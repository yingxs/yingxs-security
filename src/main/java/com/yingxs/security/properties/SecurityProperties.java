package com.yingxs.security.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统参数配置类
 * @author yingxs
 * @date 2019-10-18 11:34:56
 */

@ConfigurationProperties(prefix="yingxs.security")
public class SecurityProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();



    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}

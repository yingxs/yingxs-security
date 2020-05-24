package com.yingxs.security.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 系统参数配置类
 * @author yingxs
 * @date 2019-10-18 11:34:56
 */

@Data
@ConfigurationProperties(prefix="yingxs.security")
public class SecurityProperties {

    private ValidateCodeProperties code = new ValidateCodeProperties();

}

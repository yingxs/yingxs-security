package com.yingxs.security.authentication;

import com.yingxs.security.support.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 获取用户信息实现类
 * @author yingxs
 * @date 2019-10-9 11:03:24
 * @email ying_xs@163.com
 */
@Component
public class YingxsUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("用户名登陆:"+username);

        // 该操作应该在注册时进行，这里仅为演示使用
        String password = passwordEncoder.encode("123456");
//        return new User(username,password,true,// 可用
//                true,// 账户未过期
//                true,// 密码未过期
//                true,//是否未被锁定
//                 AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return new UserInfo(1,username,password,null,null, true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SYSTEM") ); // 可用

    }




}

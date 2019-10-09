package com.yingxs.security.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用户信息对象
 * @author yingxs
 * @date 2019-10-8 11:24:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo2 extends User {
    private long userId  ;
    private String email ;
    private String headImg = "https://img2.woyaogexing.com/2019/10/07/75f08ce15ef942298e0289c3b06a9017!400x400.jpeg";

    public UserInfo2(long userId, String username, String password, String email, String headImg, Collection<? extends GrantedAuthority> authorities) {
        this(userId,username,password,email,headImg,true,true,true,true,authorities);

    }

    public UserInfo2(long userId, String username, String password, String email, String headImg, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        if (email != null  )
            this.email = email;
        if (headImg != null  )
            this.headImg = headImg;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}

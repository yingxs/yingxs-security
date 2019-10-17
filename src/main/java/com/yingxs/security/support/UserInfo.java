package com.yingxs.security.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * 用户信息对象
 * @author yingxs
 * @date 2019-10-8 11:24:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo implements UserDetails,CredentialsContainer {
    private final long userId  ;
    private final String username ;
    private String password ;
    private String email ;
    private String headImg = "https://img2.woyaogexing.com/2019/10/07/75f08ce15ef942298e0289c3b06a9017!400x400.jpeg";

    // 账户是否未过期
    private final boolean accountNonExpired;
    // 账户是否未锁定
    private final boolean accountNonLocked;
    // 密码是否未过期
    private final boolean credentialsNonExpired;
    // 账户是否可用
    private final boolean enabled;


    public UserInfo(long userId, String username,String password, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override  // 账户是否未过期
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override // 账户是否未锁定
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override // 密码是否未过期
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override // 账户是否可用
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public long getUserId() {
        return userId;
    }



    @Override
    public String getUsername() {
        return username;
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


    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new   AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while(var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

     // 权限比较器
    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 510L;

        private AuthorityComparator() {
        }

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }


}

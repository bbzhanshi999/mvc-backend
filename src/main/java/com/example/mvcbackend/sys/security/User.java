package com.example.mvcbackend.sys.security;

import com.example.mvcbackend.sys.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
@AllArgsConstructor // 生成所有参数构造方法
@NoArgsConstructor
public class User extends BaseModel implements UserDetails {

    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 用户是否已经过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户是否已经被锁定
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户的密码是否已经过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

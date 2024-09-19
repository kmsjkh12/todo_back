package com.example.todo_back.dto.user;

import com.example.todo_back.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipalDetails implements UserDetails{
    private final User user;

    public UserPrincipalDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    //멤버 이메일 담아두기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>  authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getEmail()));
        return authorities;
    }

    //비밀번호
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    //이메일
    @Override
    public String getUsername() {
        return user.getEmail();
    }


    public String getNickname() {
        return user.getNickname();
    }
    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 비밀번호 만류 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    //계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return false;
    }
}

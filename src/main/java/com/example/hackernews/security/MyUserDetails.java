package com.example.hackernews.security;

import com.example.hackernews.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {


    private String password;
    private String username;
    private int id;

    private List<GrantedAuthority> authorities;

    public MyUserDetails() {
    }

    public MyUserDetails(User user) {

        this.username=user.getUsername();
        this.password = user.getPassword();
        this.id = user.getId();
        this.authorities =
                Arrays.
                        stream(user.getRole().split(",")).
                        map(SimpleGrantedAuthority::new).
                        collect(Collectors.toList());
        System.out.println(authorities+"authority");
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
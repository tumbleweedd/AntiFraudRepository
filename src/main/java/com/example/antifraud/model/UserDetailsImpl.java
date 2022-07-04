package com.example.antifraud.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String name;
    private final String username;
    private final String password;

    private final boolean userBlocked;
    private final List<GrantedAuthority> roleAndAuthorities;

    public UserDetailsImpl(UserModel user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userBlocked = user.isUserBlocked();
        this.roleAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    public boolean isUserBlocked() {
        return userBlocked;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleAndAuthorities;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

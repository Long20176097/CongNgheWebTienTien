package com.example.zalo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;


public class InstaUserDetails extends User implements UserDetails {


    public InstaUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return getAuthorities()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" +role.getAuthority()))
                .collect(Collectors.toSet());
    }




}

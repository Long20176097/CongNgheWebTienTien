package com.example.zalo.service;


import com.example.zalo.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user =userService.findUserByPhoneNumber(phoneNumber);
        UserBuilder userBuilder =null;

        if(user!=null ) {
            userBuilder= org.springframework.security.core.userdetails.User.withUsername(phoneNumber);
            userBuilder.password(user.getPassword());
            userBuilder.roles(user.getAuthority().getAuthority());
            if(!user.getStatus().equals("enabled")){
                userBuilder.disabled(true);
            }
        }else {
            throw new UsernameNotFoundException("Username not found");
        }

        return userBuilder.build();
    }



}
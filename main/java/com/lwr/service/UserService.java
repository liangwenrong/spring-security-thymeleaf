package com.lwr.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lwr.entity.User;

public interface UserService extends UserDetailsService{
    public User getUserByName(String name);
}

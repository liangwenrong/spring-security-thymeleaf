package com.lwr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lwr.dao.UserDao;
import com.lwr.entity.User;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
//    @Transactional
    public User getUserByName(String name) {
        List<User> list = userDao.findByName(name);
        if(list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> list = userDao.findByName(s);
        User user;
        if(list.size()>0 && list.get(0) != null) {
            user = list.get(0);
        }else {
            throw new UsernameNotFoundException("Username " + s + " not found");
        }
        //查role 表
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if("admin".equals(user.getName())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(), true, true, true, true, authorities);
    }

}

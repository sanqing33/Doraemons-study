package com.doraemons.service;

import com.doraemons.entity.User;
import com.doraemons.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Resource
    UserMapper mapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mapper.findUserByNameOrEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("user")
                .build();
    }
}


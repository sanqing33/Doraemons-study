package com.doraemons.controller;

import com.alibaba.fastjson.JSONObject;
import com.doraemons.entity.RestBean;
import com.doraemons.entity.User;
import com.doraemons.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Resource
    UserMapper mapper;

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String email,
                                           @RequestParam String password) {

        // 检查数据库是否存在重复记录
        User existingUsername = mapper.findUserByNameOrEmail(username);
        User existingEmail = mapper.findUserByNameOrEmail(email);

        if (existingUsername != null || existingEmail != null) {
            // 数据库中已存在相同的用户名或邮箱，返回错误信息
            return ResponseEntity.badRequest().body(JSONObject.toJSONString(RestBean.success("用户名或邮箱已存在")));
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);

            // 执行插入操作，将新用户信息保存到数据库中
            mapper.addUser(newUser);

            return ResponseEntity.ok().body(JSONObject.toJSONString(RestBean.success("注册成功")));
        }

    }
}

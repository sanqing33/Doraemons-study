package com.doraemons.entity;

import lombok.Data;

@Data
public class User {
    int id;
    String username;
    String password;
    String full_name;
    String email;
}
package com.doraemons.entity;

import lombok.Data;

@Data
public class RestBean<T> {
    private int status;
    private boolean success;
    private T name;
    private T message;

    private RestBean(int status, boolean success, T message, T name) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.name = name;
    }

    public static <T> RestBean<T> loginsuccess(T data, T name) {
        return new RestBean<>(200, true, data, name);
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(200, true, data, null);
    }

    public static <T> RestBean<T> failure(int status) {
        return new RestBean<>(status, false, null, null);
    }

    public static <T> RestBean<T> failure(int status, T data) {
        return new RestBean<>(status, false, data, null);
    }

}

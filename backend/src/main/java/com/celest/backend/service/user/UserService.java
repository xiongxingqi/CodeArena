package com.celest.backend.service.user;

import com.celest.backend.pojo.entity.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> getToken(String username, String password);

    User getInfo();
}

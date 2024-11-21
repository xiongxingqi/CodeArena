package com.celest.backend.service.user;

import com.celest.backend.pojo.DTO.UserRegisterDTO;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.utils.result.Result;

import java.util.Map;

public interface UserService {
    String getToken(String username, String password);

    User getInfo();

    Result<Object> register(String username, String password, String confirmedPassword);
}

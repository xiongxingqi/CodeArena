package com.celest.backend.service.user;

public interface UserService {
    String getToken(String username, String password);
}

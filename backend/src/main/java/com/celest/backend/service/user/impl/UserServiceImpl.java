package com.celest.backend.service.user.impl;

import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.impl.utils.UserDetailsImpl;
import com.celest.backend.service.user.UserService;
import com.celest.backend.utils.jwt.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Map<String, Object> getToken(String username, String password) {
        //构建AuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        Map<String, Object> struct = new HashMap<>();
        struct.put("token",JwtUtil.createJWT(user.getId().toString()));
        return struct;
    }

    @Override
    public User getInfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        return principal.getUser();
    }
}

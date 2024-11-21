package com.celest.backend.service.user.impl;

import cn.hutool.jwt.JWTUtil;
import com.celest.backend.config.properties.JwtProperties;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.impl.utils.UserDetailsImpl;
import com.celest.backend.service.user.UserService;
import com.celest.backend.utils.jwt.StringUtil;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    private final JwtProperties jwtProperties;
    @Override
    public String getToken(String username, String password) {
        //构建AuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        Map<String,Object> pay=new HashMap<>();
        pay.put("uid",user.getId());
        pay.put("expire_time",System.currentTimeMillis()+jwtProperties.getExpireTime());
        return JWTUtil.createToken(pay,jwtProperties.getSecretKey().getBytes());
    }

    @Override
    public User getInfo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        return principal.getUser();
    }

    @Override
    public Result<Object> register(String username, String password, String confirmedPassword) {
        username = username.trim();
        password = password.trim();
        confirmedPassword = confirmedPassword.trim();
        if (StringUtil.isEmpty(username))
            return Result.error("用户名不能为空");

        if(StringUtil.isEmpty(password)||StringUtil.isEmpty(confirmedPassword))
            return Result.error("密码和确认密码不能为空");
        if (username.length()>100||password.length()>100||confirmedPassword.length()>100)
            return Result.error("用户名或密码不合法");
        if (!password.equals(confirmedPassword)) {
            return Result.error("两次输入的密码不一致");
        }

        String encode = passwordEncoder.encode(password);
        User user = new User(null,username,encode,null);

        userMapper.insert(user);

        return Result.success();
    }

}

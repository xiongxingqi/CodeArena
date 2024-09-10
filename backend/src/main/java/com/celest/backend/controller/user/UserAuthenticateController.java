package com.celest.backend.controller.user;

import com.celest.backend.pojo.DTO.UserLoginDto;
import com.celest.backend.pojo.DTO.UserRegisterDTO;
import com.celest.backend.service.user.UserService;
import com.celest.backend.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="UserAuthentication",description =  "用户登录注册功能")
@Slf4j
public class UserAuthenticateController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "用户登录接口",description = "对用户进行认证,发放jwtToken")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto){
        log.info("用户:{}进行登录认证",userLoginDto.username);
        String token=userService.getToken(userLoginDto.getUsername(),userLoginDto.getPassword());
        return Result.success(token);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result<String> register(@Parameter(name = "userRegisterDTO",required = true) @RequestBody UserRegisterDTO userRegisterDTO){

        return Result.success("success");
    }

}

package com.celest.backend.controller.user;

import com.celest.backend.pojo.DTO.UserLoginDto;
import com.celest.backend.service.user.UserService;
import com.celest.backend.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="用户认证",description =  "用户登录注册功能")
public class UserLoginController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "用户登录接口",description = "对用户进行认证,发放jwtToken")
    public Result<String> login(@RequestBody UserLoginDto userLoginDto){
        String token=userService.getToken(userLoginDto.getUsername(),userLoginDto.getPassword());
        return Result.success(token);
    }

}

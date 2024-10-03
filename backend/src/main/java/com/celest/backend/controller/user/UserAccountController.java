package com.celest.backend.controller.user;

import cn.hutool.core.bean.BeanUtil;
import com.celest.backend.pojo.DTO.UserLoginDto;
import com.celest.backend.pojo.DTO.UserRegisterDTO;
import com.celest.backend.pojo.VO.UserVO;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.impl.utils.UserDetailsImpl;
import com.celest.backend.service.user.UserService;
import com.celest.backend.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/account")
@RequiredArgsConstructor
@Tag(name="UserAuthentication",description =  "用户登录注册功能")
@Slf4j
public class UserAccountController {

    private final UserService userService;


    @PostMapping("/token")
    @Operation(summary = "用户登录接口",description = "对用户进行认证,发放jwtToken")
    public Result<Map<String,Object>> login(@RequestBody UserLoginDto userLoginDto){
        log.info("用户:{}进行登录认证",userLoginDto.username);
        Map<String, Object> token=userService.getToken(userLoginDto.getUsername(),userLoginDto.getPassword());
        return Result.success(token);
    }

    @GetMapping("/info")
    public Result<UserVO> getInfo(){
        User user=userService.getInfo();
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.success(userVO);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result<String> register(@Parameter(name = "userRegisterDTO",required = true) @RequestBody UserRegisterDTO userRegisterDTO){

        return Result.success("success");
    }

}

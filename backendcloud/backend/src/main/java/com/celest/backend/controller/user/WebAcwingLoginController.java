package com.celest.backend.controller.user;

import com.celest.backend.pojo.VO.TokenVO;
import com.celest.backend.service.user.WebAcwingLoginService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acwing/web/login")
@Slf4j
@RequiredArgsConstructor
public class WebAcwingLoginController {

    private final WebAcwingLoginService webAcwingLoginService;

    @GetMapping("/applyCode")
    public Result<String> applyCode(){
        String url = webAcwingLoginService.applyCode();
        return Result.success(url);
    }
    @GetMapping("/redirect")
    public Result<TokenVO> redirectCode(String code,String state){
        TokenVO tokenVO = webAcwingLoginService.receiveCode(code,state);
        return Result.success(tokenVO);
    }
}

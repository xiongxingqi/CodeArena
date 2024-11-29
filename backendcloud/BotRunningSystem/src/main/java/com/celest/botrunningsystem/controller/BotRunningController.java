package com.celest.botrunningsystem.controller;

import com.celest.botrunningsystem.service.BotRunningService;
import com.celest.botrunningsystem.utils.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/botRunning")
@Slf4j
@RequiredArgsConstructor
public class BotRunningController {
    private final BotRunningService botRunningService;

    @PostMapping("/addBot")
    public Result<?> addBot(@RequestParam MultiValueMap<String,String> data){
        log.info("add success");
        String userId = data.getFirst("userId");
        String botCode = data.getFirst("botCode");
        String input = data.getFirst("input");
        botRunningService.addBot(userId,botCode,input);
        return Result.success();
    }
}

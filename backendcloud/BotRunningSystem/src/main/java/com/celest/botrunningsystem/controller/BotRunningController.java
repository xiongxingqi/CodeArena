package com.celest.botrunningsystem.controller;

import com.celest.botrunningsystem.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/botRunning")
@Slf4j
public class BotRunningController {

    @PostMapping("/addBot")
    public Result<?> addBot(@RequestParam MultiValueMap<String,String> data){
        log.info("add success");

        return Result.success();
    }
}

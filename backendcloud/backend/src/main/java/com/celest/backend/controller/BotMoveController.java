package com.celest.backend.controller;

import com.celest.backend.service.bot.BotMoveService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/botMove")
@RequiredArgsConstructor
public class BotMoveController {


    private final BotMoveService botMoveService;

    @PostMapping("/setDirection")
    public Result<?> setBotDirection(@RequestParam MultiValueMap<String,String> data){
        String userId = data.getFirst("userId");
        String direction = data.getFirst("direction");
        botMoveService.setDirection(userId,direction);
        return Result.success();
    }
}

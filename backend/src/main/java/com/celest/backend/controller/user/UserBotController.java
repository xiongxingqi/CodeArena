package com.celest.backend.controller.user;

import com.celest.backend.pojo.entity.Bot;
import com.celest.backend.service.user.BotService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/bot")
@RequiredArgsConstructor
public class UserBotController {

    private final BotService botService;

    @GetMapping("/getList")
    public Result<List<Bot>>  getBotList(){
        List<Bot> bots = botService.getBotList();
        return Result.success(bots);
    }

}

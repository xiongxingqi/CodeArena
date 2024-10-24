package com.celest.backend.controller.user;

import com.celest.backend.pojo.DTO.BotDto;
import com.celest.backend.pojo.entity.Bot;
import com.celest.backend.service.user.BotService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/bot")
@RequiredArgsConstructor
@Slf4j
public class UserBotController {

    private final BotService botService;

    @GetMapping("/getList")
    public Result<List<Bot>>  getBotList(){
        log.info("用bot列表");
        List<Bot> bots = botService.getBotList();
        return Result.success(bots);
    }

    @PostMapping("/addBot")
    public Result<?> addBot(@RequestBody BotDto botDto){
        log.info("用户添加bot");
        botService.insertBot(botDto);
        return Result.success();
    }
    @DeleteMapping("/remove")
    public Result<?> removeBot(Integer id){
        log.info("用户删除bot");
        botService.deleteBot(id);
        return Result.success();
    }
    @PostMapping("/update")
    public Result<?> modifyBot(@RequestBody Bot bot){
        log.info("修改bot,bot的id为:{}",bot.getId());
        botService.updateBot(bot);
        return Result.success();
    }

}

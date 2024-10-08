package com.celest.backend.controller.pk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pk")
public class BotInfoController {
    @GetMapping("/info")
    public Map<String, String> getInfo(){
        Map<String,String> bot=new HashMap<>();
        bot.put("name","tiger");
        bot.put("rating","1500");
        System.out.println("66666");
        return bot;
     }

}

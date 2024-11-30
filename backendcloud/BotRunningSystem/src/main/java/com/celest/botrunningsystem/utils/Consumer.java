package com.celest.botrunningsystem.utils;

import com.celest.botrunningsystem.runningbot.BotInterface;
import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@Slf4j
public class Consumer extends Thread{

    private static RestTemplate restTemplate;
    private Bot bot;

    private final static String  BotMoveUrl = "http://127.0.0.1:3000/botMove/setDirection";
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        Consumer.restTemplate = restTemplate;
    }

    public void setTimeOut(Bot bot ,int mill) throws InterruptedException {
        this.bot = bot;

        this.start();
        try{
            this.join(mill);
        }finally {
            this.interrupt();
        }
    }

    @Override
    public void run() {
        UUID uid = UUID.randomUUID();
        String uuid = uid.toString().substring(0, 8);

        BotInterface botInterface =Reflect.compile("com.celest.botrunningsystem.runningbot.Bot" + uuid,
                addUUid(bot.getCode(), uuid)).create().get();

        int direction = botInterface.move(bot.getInput());
        log.info("move-direction: {} userId: {}",direction,bot.getUserId());
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId",String.valueOf(bot.getUserId()));
        data.add("direction",String.valueOf(direction));

        Result<?> res = restTemplate.postForObject(BotMoveUrl, data, Result.class);
        if(res!= null)
            log.info("setDirection: {}",res.getMessage());
    }

    private String addUUid(String code, String uuid) {
        int index = code.indexOf(" implements com.celest.botrunningsystem.runningbot.BotInterface");
        return code.substring(0,index) + uuid + code.substring(index);
    }
}

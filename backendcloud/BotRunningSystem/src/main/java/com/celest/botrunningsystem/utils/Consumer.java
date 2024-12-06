package com.celest.botrunningsystem.utils;
import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;

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

        Supplier<Integer> botInterface =Reflect.compile("com.celest.botrunningsystem.runningbot.Bot" + uuid,
                addUUid(bot.getCode(), uuid)).create().get();
        File file = new File("input.txt");
        try(PrintWriter fileOut = new PrintWriter(file)){
            fileOut.println(bot.getInput());
            fileOut.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int direction = botInterface.get();
        log.info("move-direction: {} userId: {}",direction,bot.getUserId());
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId",String.valueOf(bot.getUserId()));
        data.add("direction",String.valueOf(direction));

        Result<?> res = restTemplate.postForObject(BotMoveUrl, data, Result.class);
        if(res!= null)
            log.info("setDirection: {}",res.getMessage());
    }

    private String addUUid(String code, String uuid) {
        int index = code.indexOf(" implements java.util.function.Supplier<Integer>");
        return code.substring(0,index) + uuid + code.substring(index);
    }
}

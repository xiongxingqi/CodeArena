package com.celest.botrunningsystem;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BotRunningSystemApplication {

    @Getter
    private static  ConfigurableApplicationContext context;

    public static void main(String[] args) {
         context = SpringApplication.run(BotRunningSystemApplication.class, args);
    }
}
package com.celest.backend;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BackendApplication {

    @Getter
    private  static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(BackendApplication.class, args);
    }

}

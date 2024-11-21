package com.celest.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI getOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("KingOfBots BackendAPI 管理")
                        .contact(new Contact().name("celeStrong").email("1269504317@qq.com").url("https://github.com/xiongxingqi"))
                        .version("1.0.0")
                        .description("KingOfBots API")
                        .license(new License().name("Apache 2.0")));
    }
}

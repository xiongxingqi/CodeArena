package com.celest.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "backend.jwt")
@Data
public class JwtProperties {
    private String secretKey;
    private Long expireTime;
}

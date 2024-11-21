package com.celest.backend;

import cn.hutool.jwt.JWTUtil;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ToolsTest {
    @Test
    public void  UUIDTest(){
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }

    @Test
    public  void mapTest(){
        Map<String, Object> map = new HashMap<String, Object>() {
            @Serial
            private static final long serialVersionUID = 1L;
            {
                put("uid", Integer.parseInt("123"));
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        JWTUtil.createToken(map, "1234".getBytes());

    }
}

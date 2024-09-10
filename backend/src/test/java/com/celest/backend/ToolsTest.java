package com.celest.backend;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ToolsTest {
    @Test
    public void  UUIDTest(){
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
}

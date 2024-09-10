package com.celest.backend.utils.jwt;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JwtUtil {
    public static long JWT_TTL= 60*60*1000L*24*14;
    public static String JWT_KEY="SDFGjhdsfalshdfHFdsjkdsfds121232131afasdfac";

    public static @NotNull String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    
}

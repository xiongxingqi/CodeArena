package com.celest.backend.utils.jwt;

import io.jsonwebtoken.*;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    public static long JWT_TTL= 60*60*1000L*24*14;
    public static String JWT_KEY="SDFGjhdsfalshdfHFdsjkdsfds121232131afasdfac";

    public static @NotNull String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createJWT(String subject){
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());
        return builder.compact();
    }
    public static JwtBuilder getJwtBuilder(String subject,Long ttlMillis,String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .id(uuid)
                .subject(subject)
                .issuer("sg")
                .issuedAt(now)
                .signWith(signatureAlgorithm,secretKey)
                .expiration(expDate);
    }

    public static SecretKey generalKey(){
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodeKey,0,encodeKey.length,"HmacSHA256");
    }
    public static Claims parseJwt(String jwt) throws JwtException, IllegalArgumentException {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseUnsecuredClaims(jwt)
                .getPayload();
    }
}

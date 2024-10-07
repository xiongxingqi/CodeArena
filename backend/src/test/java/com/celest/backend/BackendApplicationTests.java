package com.celest.backend;

import cn.hutool.jwt.JWTUtil;
import com.celest.backend.config.properties.JwtProperties;
import com.celest.backend.pojo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private JwtProperties jwtProperties;
    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String encode = passwordEncoder.encode("123456");

        System.out.println(encode);

        System.out.println(passwordEncoder.matches("123456",encode));
    }
    @Test
    public void securityTest(){
        StringKeyGenerator stringKeyGenerator = KeyGenerators.string();
        BytesEncryptor stronger = Encryptors.stronger("20031018", stringKeyGenerator.generateKey());
        byte[] encrypt = stronger.encrypt("Hello World".getBytes());
        byte[] decrypt = stronger.decrypt(encrypt);
        System.out.println(Arrays.toString(encrypt));
        String s = new String(decrypt);
        System.out.println(s);
    }
    @Test
    public void memoryTest(){

        User user =getUser();
        System.out.println(user.hashCode());
        System.out.println("内存地址");
        System.out.println(System.identityHashCode(user));
    }

    private User getUser() {
        User user = new User(1, "celesta", "123456");
        System.out.println(user.hashCode());
        System.out.println("内存地址");
        System.out.println(System.identityHashCode(user));
        return user;
    }

    @Test
    public void textPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        System.out.println(encoder.encode("123456"));

    }
    @Test
    public void testJwt(){
        Map<String, Object> map = new HashMap<>() {
            @Serial
            private static final long serialVersionUID = 1L;

            {
                put("uid", Integer.parseInt("123"));
                put("expire_time", System.currentTimeMillis() + jwtProperties.getExpireTime());
            }
        };

        String token = JWTUtil.createToken(map, jwtProperties.getSecretKey().getBytes());

        JWTUtil.parseToken(token);

        boolean verify = JWTUtil.verify(token, jwtProperties.getSecretKey().getBytes());

    }

}

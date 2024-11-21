package com.celest.backend;

import cn.hutool.jwt.JWTUtil;
import com.celest.backend.config.properties.JwtProperties;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.utils.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RestTemplate restTemplate;
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
        System.out.println("内存地址");
        System.out.println(System.identityHashCode(user));
    }

    private User getUser() {
        return null;
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
    @Test
    public void rpcTest(){
        String url = "http://localhost:3001/player/add";
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("user_id","1223");
        body.add("rating","1500");
        Result<?> result = restTemplate.postForObject(url, body, Result.class);
        System.out.println(result);

    }

}

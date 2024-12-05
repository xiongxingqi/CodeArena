package com.celest.backend;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.celest.backend.config.properties.JwtProperties;
import com.celest.backend.controller.user.UserAccountController;
import com.celest.backend.pojo.DTO.UserLoginDto;
import com.celest.backend.pojo.DTO.UserRegisterDTO;
import com.celest.backend.pojo.VO.TokenVO;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserAccountController userAccountController;
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

    @Test
    public void userLoginTest(){
        Result<TokenVO> result ;
        //模拟用户登录请求
        UserLoginDto user = new UserLoginDto("celestrong", "123456");
        //调用接口
         result = userAccountController.login(user);
         //检测测试结果 若code为 1 成功 否则 失败
         boolean testResult = result.getCode() == 1;
         //输出测试结果
        System.out.println("testResult: "+ testResult + "entity: " + JSONUtil.toJsonStr(result));

    }
    @Test
    public void userRegisterTest(){
        Result<?> result ;
        //模拟用户注册
        UserRegisterDTO registeruser = new UserRegisterDTO("registeruser", "159753", "159753");
        //调用接口
        result = userAccountController.register(registeruser);
        //检测测试结果 若code为 1 成功 否则 失败
        boolean testResult = result.getCode() == 1;
        //输出测试结果
        System.out.println("testResult: "+ testResult + "entity: " + JSONUtil.toJsonStr(result));

    }



}

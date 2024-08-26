package com.celest.backend;

import com.celest.backend.pojo.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

//@SpringBootTest
class BackendApplicationTests {

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


}

package com.celest.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public List<User> getAll(){
        System.out.println("查询所有用户");
        return userMapper.selectList(null);
    }

    @GetMapping("/{userId}")
    public User getUserById( @PathVariable Integer userId){
        QueryWrapper<User> queryWrapper = Wrappers.query(User.class).eq("id", userId);
        return userMapper.selectOne(queryWrapper);
    }
    @GetMapping ("/add/{userId}/{username}/{password}")
    public String register(@PathVariable Integer userId,@PathVariable String username,@PathVariable String password){
        String encode = this.passwordEncoder.encode(password);
        User user = User.builder().id(userId).username(username).password(encode).build();
        userMapper.insert(user);
        return "success";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@RequestParam @PathVariable Integer userId){
        userMapper.deleteById(userId);
        return "success";
    }
}

package com.celest.backend.service.user.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.celest.backend.config.properties.JwtProperties;
import com.celest.backend.exception.BaseException;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.VO.TokenVO;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.user.WebAcwingLoginService;
import com.celest.backend.utils.HttpClientUtil;
import com.celest.backend.utils.jwt.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebAcwingLoginServiceImpl implements WebAcwingLoginService {

    private final UserMapper userMapper;
    private final RedisTemplate<String,String> redisTemplate;
    private final JwtProperties jwtProperties;

    private static final String  appId = "7275";
    private static final String  appSecret = "aaa5a0706df24197b389e01ac20f6d69";
    private static final String applyCodeUrl = "https://www.acwing.com/third_party/api/oauth2/web/authorize/";
    private static final String applyAccessTokenUrl = "https://www.acwing.com/third_party/api/oauth2/access_token/";
    private static final String applyUserUInfoUrl = "https://www.acwing.com/third_party/api/meta/identity/getinfo/";
    private static final String  redirectPage = "https://app7275.acapp.acwing.com.cn/web/acwing/login/";
    private static final Random random = new Random();

    @Override
    public String applyCode() {


        String encode = URLEncoder.encode(redirectPage, StandardCharsets.UTF_8);

        StringBuilder builder = new StringBuilder();

        for (int i =0;i < 10;i++){
            int num = random.nextInt(10);
            builder.append(num);
        }
        String state = builder.toString();
        redisTemplate.opsForValue().set(state,"true");
        redisTemplate.expire(state, Duration.ofMinutes(10));
        return applyCodeUrl.concat("?appid=").concat(appId).concat("&redirect_uri=").concat(encode)
                .concat("&scope=userinfo").concat("&state=").concat(state);
    }

    @Override
    public TokenVO receiveCode(String code, String state) {
        if(StringUtil.isEmpty(code)||StringUtil.isEmpty(state)) throw new BaseException("参数不能为空!");
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(state))) throw new BaseException("state是过期或不正确");
        redisTemplate.delete(state);
        List<NameValuePair> params  = new ArrayList<>();
        TokenVO tokenVO = new TokenVO();
        params.add(new BasicNameValuePair("appid",appId));
        params.add(new BasicNameValuePair("secret",appSecret));
        params.add(new BasicNameValuePair("code",code));
        String res = HttpClientUtil.get(applyAccessTokenUrl, params);
        if (res == null)  throw new BaseException("请求异常");
        log.info("{}",res);
        JSONObject entries = JSONUtil.parseObj(res);
        String accessToken = entries.getStr("access_token");
        String openid = entries.getStr("openid");
        if(StringUtil.isEmpty(accessToken)||StringUtil.isEmpty(openid)) throw new BaseException("accessToken申请失败!");
        LambdaQueryWrapper<User> eq = Wrappers.lambdaQuery(User.class).eq(User::getOpenid, openid);
        User user = userMapper.selectOne(eq);
        Map<String,Object> claims = new HashMap<>();
        if (user != null) {
            claims.put("uid",user.getId());
            claims.put("expire_time",jwtProperties.getExpireTime() + System.currentTimeMillis());
            String token = JWTUtil.createToken(claims,jwtProperties.getSecretKey().getBytes());
            tokenVO.setToken(token);
            return tokenVO;
        }

        params.clear();
        params.add(new BasicNameValuePair("openid" ,openid));
        params.add(new BasicNameValuePair("access_token" ,accessToken));

         res = HttpClientUtil.get(applyUserUInfoUrl, params);
         if(res == null ) throw new BaseException("用信息获取失败");
         log.info("第三方信息为:{}",res);
         JSONObject userInfo = JSONUtil.parseObj(res);
        String username = userInfo.get("username", String.class);
        String photo = userInfo.get("photo", String.class);
        if (username == null || username.isEmpty() || photo == null || photo.isEmpty())  throw new BaseException("第三方信息为空");

        for(int i=0;i<100;i++){
            eq = Wrappers.lambdaQuery(User.class).eq(User::getUsername, username);
            user = userMapper.selectOne(eq);
            if(user == null) break;
            if(i==99) throw new BaseException("用户名异常");
            username+=random.nextInt(10);
        }
        user = new User(null,username,"123456",1500,photo,openid);
        userMapper.insert(user);
        claims.put("uid",user.getId());
        claims.put("expire_time",jwtProperties.getExpireTime() + System.currentTimeMillis());
        String token = JWTUtil.createToken(claims, jwtProperties.getSecretKey().getBytes());
        tokenVO.setToken(token);
        return tokenVO;
    }
}

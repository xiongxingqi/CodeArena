package com.celest.backend.comsumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.entity.User;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public static ConcurrentHashMap<Integer, WebSocketServer>  users = new ConcurrentHashMap<>();

    private Session session;

    private User user;

    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper= userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        // 建立连接
        System.out.println("connect!");
        this.session = session;
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject claims = jwt.getPayload().getClaimsJson();
        Integer userId = claims.get("uid", Integer.class);
        this.user = userMapper.selectById(userId);
        users.put(user.getId(),this);
        System.out.println(user);

    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("closed!");
        if (this.user != null) {
            users.remove(user.getId());
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    public void sendMessage(String message) {

        synchronized (this.session){
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
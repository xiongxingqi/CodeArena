package com.celest.backend.comsumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.entity.User;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public final static ConcurrentHashMap<Integer, WebSocketServer>  users = new ConcurrentHashMap<>();

    public final static CopyOnWriteArraySet<User> matchPool = new CopyOnWriteArraySet<>();

    private Session session;

    private User user;

    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper= userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        System.out.println("connect!");
        this.session = session;
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject claims = jwt.getPayload().getClaimsJson();
        Integer userId = claims.get("uid", Integer.class);

        this.user = userMapper.selectById(userId);
        if (this.user == null) {
            session.close();
        }else {
            users.put(user.getId(),this);
        }

        System.out.println(user);

    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("closed!");
        if (this.user != null) {
            users.remove(user.getId());
            matchPool.remove(this.user);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        JSONObject info = JSONUtil.parseObj(message);
        String event = info.getStr("event");
        if(event.equals("start_match")){
            startMatch();
        }else if(event.equals("stop_match")){
            stopMatch();
        }

    }

    private void stopMatch() {
        System.out.println("stopMatch");
        matchPool.remove(this.user);
    }

    private void startMatch() {
        System.out.println("start match");
        matchPool.add(this.user);

        while(matchPool.size()>=2){
            Iterator<User> iterator = matchPool.iterator();
            User playerOne = iterator.next(),playerTwo = iterator.next();

            matchPool.remove(playerTwo);
            matchPool.remove(playerOne);

            JSONObject resA = new JSONObject();
            resA.set("event","match_success");
            resA.set("opponent_username",playerOne.getUsername());
            resA.set("opponent_photo",playerOne.getPhoto());
            users.get(playerTwo.getId()).sendMessage(resA.toString());

            JSONObject resB = new JSONObject();
            resB.set("event","match_success");
            resB.set("opponent_username",playerTwo.getUsername());
            resB.set("opponent_photo",playerTwo.getPhoto());
            users.get(playerOne.getId()).sendMessage(resB.toString());

        }


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
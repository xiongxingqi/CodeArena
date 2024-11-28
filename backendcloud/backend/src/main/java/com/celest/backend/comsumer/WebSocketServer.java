package com.celest.backend.comsumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.celest.backend.mapper.RecordMapper;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.utils.game.Game;
import com.celest.backend.utils.result.Result;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public final static ConcurrentHashMap<Integer, WebSocketServer>  users = new ConcurrentHashMap<>();

    private Session session;


    private User user;

    private static UserMapper userMapper;

    public static RecordMapper recordMapper;

    public static RestTemplate restTemplate;

    private  Game game;

    private static final String addPlayerUrl = "http://127.0.0.1:3001/player/add";
    private static final String removePlayerUrl = "http://127.0.0.1:3001/player/remove";
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper= userMapper;
    }
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        WebSocketServer.restTemplate = restTemplate;
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
        }
    }

    private void move(Integer direction){
        if(game.getPlayerA().getId().equals(user.getId())){
            game.setNextstepA(direction);
        }else if(game.getPlayerB().getId().equals(user.getId())){
            game.setNextstepB(direction);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        JSONObject info = JSONUtil.parseObj(message);
        String event = info.getStr("event");
        switch (event) {
            case "start_match" -> startMatch();
            case "stop_match" -> stopMatch();
            case "move" -> {
                System.out.println("move");
                move(info.getInt("direction"));
            }
        }

    }

    private void stopMatch() {
        System.out.println("stopMatch");
        MultiValueMap<String,String> data = new LinkedMultiValueMap<>();
        data.add("userId",user.getId().toString());
        Result<?> success = restTemplate.postForObject(removePlayerUrl, data, Result.class);


    }

    public static void  startGame(Integer playerA ,Integer playerB){

        User playerOne = userMapper.selectById(playerA);
        User playerTwo = userMapper.selectById(playerB);

        Game game = new Game(13,14,20,playerOne.getId(),playerTwo.getId());

        game.createMap();
        if (users.get(playerOne.getId()) != null)
            WebSocketServer.users.get(playerOne.getId()).game = game;
        if(users.get(playerTwo.getId()) != null)
            WebSocketServer.users.get(playerTwo.getId()).game = game;

        JSONObject respGame = new JSONObject();
        respGame.set("a_id",game.getPlayerA().getId());
        respGame.set("a_sx",game.getPlayerA().getSx());
        respGame.set("a_sy",game.getPlayerA().getSy());
        respGame.set("b_id",game.getPlayerB().getId());
        respGame.set("b_sx",game.getPlayerB().getSx());
        respGame.set("b_sy",game.getPlayerB().getSy());
        respGame.set("game_map",game.getMap());

        JSONObject resA = new JSONObject();
        resA.set("event","match_success");
        resA.set("opponent_username",playerOne.getUsername());
        resA.set("opponent_photo",playerOne.getPhoto());
        resA.set("game", respGame);
        if (users.get(playerTwo.getId()) != null)
            users.get(playerTwo.getId()).sendMessage(resA.toString());

        JSONObject resB = new JSONObject();
        resB.set("event","match_success");
        resB.set("opponent_username",playerTwo.getUsername());
        resB.set("opponent_photo",playerTwo.getPhoto());
        resB.set("game", respGame);
        if (users.get(playerOne.getId()) != null)
            users.get(playerOne.getId()).sendMessage(resB.toString());
        System.out.println("match_success");
        game.start();
    }

    private void startMatch() {
        System.out.println("start match");

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("userId",user.getId().toString());
        data.add("rating",user.getRating().toString());
        Result<?> success = restTemplate.postForObject(addPlayerUrl, data, Result.class);
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
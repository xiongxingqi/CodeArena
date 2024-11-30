package com.celest.backend.service.bot.impl;

import com.celest.backend.comsumer.WebSocketServer;
import com.celest.backend.service.bot.BotMoveService;
import com.celest.backend.utils.game.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BotMoveServiceImp implements BotMoveService {
    @Override
    public void setDirection(String userId, String direction) {
        log.info("user:{} direction: {}",userId,direction);
        int userIdInt = Integer.parseInt(userId);
        if (WebSocketServer.users.get(userIdInt) != null) {
            Game game = WebSocketServer.users.get(Integer.parseInt(userId)).game;
            if (game != null) {
                if(game.getPlayerA().getId().equals(userIdInt)){
                    game.setNextstepA(Integer.parseInt(direction));
                }else if(game.getPlayerB().getId().equals(userIdInt)){
                    game.setNextstepB(Integer.parseInt(direction));
                }
            }
        }
    }
}

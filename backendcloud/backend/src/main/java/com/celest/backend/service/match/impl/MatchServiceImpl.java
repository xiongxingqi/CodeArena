package com.celest.backend.service.match.impl;

import com.celest.backend.comsumer.WebSocketServer;
import com.celest.backend.service.match.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {
    @Override
    public void startGame(int userA,int aBotId, int userB,int bBotId) {
        System.out.println("startGame: userA:"+userA+ " userB: "+ userB);
        log.info("startGame: userA: {},aBotId: {},userB: {},bBotId: {}",userA,aBotId,userB,bBotId);
        WebSocketServer.startGame(userA,aBotId,userB,bBotId);
    }
}

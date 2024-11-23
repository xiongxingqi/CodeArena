package com.celest.backend.service.match.impl;

import com.celest.backend.comsumer.WebSocketServer;
import com.celest.backend.service.match.MatchService;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {
    @Override
    public void startGame(int userA, int userB) {
        System.out.println("startGame: userA:"+userA+ " userB: "+ userB);
        WebSocketServer.startGame(userA,userB);
    }
}

package com.celest.matchingsystem.service.impl;

import com.celest.matchingsystem.service.MatchingService;
import com.celest.matchingsystem.utils.MatchPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingServiceImpl implements MatchingService {

    private final MatchPool matchPool;


    @Override
    public void addPlayer(int userId, int rating,int botId) {
        log.info("addPlayer userId: {},rating: {},botId: {}", userId, rating, botId);
        matchPool.addUser(userId,rating,botId);
    }

    @Override
    public void removePlayer(int userId) {
        System.out.println("removePlayer userId:"+ userId);
        matchPool.removeUser(userId);
    }
}

package com.celest.matchingsystem.service.impl;

import com.celest.matchingsystem.service.MatchingService;
import com.celest.matchingsystem.utils.MatchPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MatchPool matchPool;


    @Override
    public void addPlayer(int userId, int rating) {
        System.out.println("addPlayer userId:"+ userId+ " rating: " + rating);
        matchPool.addUser(userId,rating);
    }

    @Override
    public void removePlayer(int userId) {
        System.out.println("removePlayer userId:"+ userId);
        matchPool.removeUser(userId);
    }
}

package com.celest.matchingsystem.service;

public interface MatchingService {
    void addPlayer(int userId, int rating,int botId);

    void removePlayer(int userId);
}

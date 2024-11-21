package com.celest.matchingsystem.service;

public interface MatchingService {
    void addPlayer(int userId, int rating);

    void removePlayer(int userId);
}

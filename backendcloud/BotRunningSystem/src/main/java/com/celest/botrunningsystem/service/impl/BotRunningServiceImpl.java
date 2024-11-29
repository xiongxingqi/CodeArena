package com.celest.botrunningsystem.service.impl;

import com.celest.botrunningsystem.service.BotRunningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BotRunningServiceImpl implements BotRunningService {
    @Override
    public void addBot(String userId, String botCode, String input) {
        log.info("userId: {},botId: {},input: {}",userId,botCode, input);
    }
}

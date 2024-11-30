package com.celest.botrunningsystem.service.impl;

import com.celest.botrunningsystem.service.BotRunningService;
import com.celest.botrunningsystem.utils.BotPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotRunningServiceImpl implements BotRunningService {
    private final BotPool botPool;
    @Override
    public void addBot(String userId, String botCode, String input) {
//        log.info("userId: {},botId: {},input: {}",userId,botCode, input);
        botPool.addBot(Integer.parseInt(userId),botCode,input);
    }
}

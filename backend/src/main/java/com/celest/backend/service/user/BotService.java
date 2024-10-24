package com.celest.backend.service.user;

import com.celest.backend.pojo.DTO.BotDto;
import com.celest.backend.pojo.entity.Bot;

import java.util.List;

public interface BotService {
    List<Bot> getBotList();

    void insertBot(BotDto botDto);

    void deleteBot(Integer id);

    void updateBot(Bot bot);

}

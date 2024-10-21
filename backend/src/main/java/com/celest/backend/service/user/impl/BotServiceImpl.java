package com.celest.backend.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.celest.backend.mapper.BotMapper;
import com.celest.backend.pojo.entity.Bot;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.impl.utils.UserDetailsImpl;
import com.celest.backend.service.user.BotService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotServiceImpl implements BotService {
    private BotMapper botMapper;
    @Override
    public List<Bot> getBotList() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        LambdaQueryWrapper<Bot> eq = Wrappers.<Bot>lambdaQuery().eq(Bot::getId, user.getId());

        return botMapper.selectList(eq);
    }
}

package com.celest.backend.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.celest.backend.exception.BaseException;
import com.celest.backend.mapper.BotMapper;
import com.celest.backend.pojo.DTO.BotDto;
import com.celest.backend.pojo.entity.Bot;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.impl.utils.UserDetailsImpl;
import com.celest.backend.service.user.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {
    
    private final BotMapper botMapper;

    @Override
    public List<Bot> getBotList() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        LambdaQueryWrapper<Bot> eq = Wrappers.<Bot>lambdaQuery().eq(Bot::getId, user.getId());

        return botMapper.selectList(eq);
    }

    @Override
    public void insertBot(BotDto botDto) throws BaseException {
        String title = botDto.getTitle();
        String description = botDto.getDescription();
        String content = botDto.getContent();
        //校验参数
        parameterVerification(title, description, content);
        //获取用户上下文
        UserDetailsImpl userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetail.getUser();
        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, 1500, now, now);
        //持久化bot
        botMapper.insert(bot);

    }

    private static void parameterVerification(String title, String description, String content) throws BaseException{
        if (title == null|| title.isEmpty()) throw new BaseException("bot的标题不能为空");
        if (title.length()>50)  throw new BaseException("bot的标题长度应小于50");
        if (description ==null|| description.isEmpty())  throw  new BaseException("bot的简介不能为空");
        if (description.length()>300) {
            throw new BaseException("bot的简介应小于300");
        }
        if (content ==null|| content.isEmpty()) {
            throw new BaseException("代码不能为空");
        }
        if (content.length()>10000) {
            throw new BaseException("代码长度应小于10000");
        }
    }
}

package com.celest.backend.service.ranklist.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.celest.backend.exception.BaseException;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.VO.RankListVO;
import com.celest.backend.pojo.VO.UserVO;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.ranklist.RankListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankListServiceImpl implements RankListService {
    private final UserMapper userMapper;
    @Override
    public RankListVO getPageList(Integer page) {
        if(page <= 0) throw new BaseException("数据不合法");
        RankListVO res = new RankListVO();
        IPage<User>  iPage = new Page<>(page,3);
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).orderByDesc(User::getRating);
        IPage<User> userIPage = userMapper.selectPage( iPage, wrapper );
        res.setPlayerTotal(userIPage.getTotal());
        List<User> records = userIPage.getRecords();
        ArrayList<UserVO> userVOS = new ArrayList<>();
        for (User record : records) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(record,userVO);
            userVOS.add(userVO);
        }
        res.setPlayers(userVOS);
        return res;
    }
}

package com.celest.matchingsystem.controller;

import com.celest.matchingsystem.service.MatchingService;
import com.celest.matchingsystem.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    @PostMapping("/player/add")
    //使用@RequestParam可将映射参数强制转为MultiValueMap
    public Result<?> addPlayer(@RequestParam MultiValueMap<String,String> data){
        int userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("userId")));
        int rating = Integer.parseInt(Objects.requireNonNull(data.getFirst("rating")));
        this.matchingService.addPlayer(userId,rating);
        return Result.success();
    }

    @PostMapping("/player/remove")
    public Result<?> removePlayer(MultiValueMap<String,String> data){
        int userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        matchingService.removePlayer(userId);
        return Result.success();
    }
}

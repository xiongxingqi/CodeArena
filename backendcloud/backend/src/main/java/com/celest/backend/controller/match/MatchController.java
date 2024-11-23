package com.celest.backend.controller.match;

import com.celest.backend.service.match.MatchService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;



    @PostMapping("/startGame")
    public Result<?> startGame(@RequestParam MultiValueMap<String,String> data){
        int userA = Integer.parseInt(Objects.requireNonNull(data.getFirst("userA")));
        int userB = Integer.parseInt(Objects.requireNonNull(data.getFirst("userB")));
        matchService.startGame(userA,userB);
        return Result.success();
    }
}

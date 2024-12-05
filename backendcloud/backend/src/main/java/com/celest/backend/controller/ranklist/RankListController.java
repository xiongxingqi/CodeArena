package com.celest.backend.controller.ranklist;

import com.celest.backend.pojo.VO.RankListVO;
import com.celest.backend.service.ranklist.RankListService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rank")
@Slf4j
@RequiredArgsConstructor
public class RankListController {

    private final RankListService rankListService;

    @GetMapping("/getPageList")
    public Result<RankListVO> getPageList(Integer page){
        log.info("currentPage: {}",page);
        RankListVO vo = rankListService.getPageList(page);
        return Result.success(vo);
    }
}

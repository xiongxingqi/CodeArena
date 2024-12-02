package com.celest.backend.controller.record;

import com.celest.backend.pojo.VO.RecordVO;
import com.celest.backend.service.record.RecordService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/getRecordList")
    private Result<List<RecordVO>>  getRecordList(@RequestParam(name = "page") Integer page){
        List<RecordVO> recordVOS = recordService.getRecordList(page);
        return Result.success(recordVOS);
    }
}

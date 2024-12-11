package com.celest.backend.controller.record;

import com.celest.backend.pojo.entity.Record;
import com.celest.backend.service.record.RecordService;
import com.celest.backend.utils.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/getRecordList")
    public  Result<Map<String,Object>>  getRecordList(@RequestParam(name = "page") Integer page){
        Map<String,Object> recordVOS = recordService.getRecordList(page);
        return Result.success(recordVOS);
    }
}

package com.celest.backend.service.record;

import com.celest.backend.pojo.VO.RecordVO;

import java.util.List;
import java.util.Map;

public interface RecordService {
    Map<String,Object> getRecordList(Integer currentPage);

}

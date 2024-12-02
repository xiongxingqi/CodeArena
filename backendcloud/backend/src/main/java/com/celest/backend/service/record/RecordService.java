package com.celest.backend.service.record;

import com.celest.backend.pojo.VO.RecordVO;

import java.util.List;

public interface RecordService {
    List<RecordVO> getRecordList(Integer currentPage);

}

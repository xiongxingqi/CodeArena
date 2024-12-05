package com.celest.backend.service.ranklist;

import com.celest.backend.pojo.VO.RankListVO;

public interface RankListService {
    RankListVO getPageList(Integer page);
}

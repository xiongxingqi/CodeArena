package com.celest.backend.service.user;

import com.celest.backend.pojo.VO.TokenVO;

public interface WebAcwingLoginService {
    String applyCode();
    TokenVO receiveCode(String code,String state);
}

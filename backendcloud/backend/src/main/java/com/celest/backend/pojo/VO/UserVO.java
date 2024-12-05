package com.celest.backend.pojo.VO;

import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String username;
    private String photo;
    private Integer rating;
}

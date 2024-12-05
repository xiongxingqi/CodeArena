package com.celest.backend.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankListVO {
    private Long playerTotal;
    private List<UserVO> players;
}

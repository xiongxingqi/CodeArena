package com.celest.backend.pojo.VO;

import com.celest.backend.pojo.entity.Record;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordVO {
    private String avatarA;
    private String playerNameA;
    private String avatarB;
    private String playerNameB;
    private Record record;
}

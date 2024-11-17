package com.celest.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celest.backend.pojo.entity.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}

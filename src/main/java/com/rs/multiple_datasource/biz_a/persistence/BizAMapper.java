package com.rs.multiple_datasource.biz_a.persistence;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BizAMapper {
    String selectTest();
}

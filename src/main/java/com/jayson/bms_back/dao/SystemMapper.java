package com.jayson.bms_back.dao;

import com.jayson.bms_back.pojo.System;
import com.jayson.bms_back.pojo.SystemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemMapper {
    int countByExample(SystemExample example);

    int deleteByExample(SystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(System record);

    int insertSelective(System record);

    List<System> selectByExample(SystemExample example);

    System selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") System record, @Param("example") SystemExample example);

    int updateByExample(@Param("record") System record, @Param("example") SystemExample example);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);
}

package com.jayson.bms_back.dao;

import com.jayson.bms_back.pojo.UserCount;
import com.jayson.bms_back.pojo.UserCountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCountMapper {
    int countByExample(UserCountExample example);

    int deleteByExample(UserCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCount record);

    int insertSelective(UserCount record);

    List<UserCount> selectByExample(UserCountExample example);

    UserCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCount record, @Param("example") UserCountExample example);

    int updateByExample(@Param("record") UserCount record, @Param("example") UserCountExample example);

    int updateByPrimaryKeySelective(UserCount record);

    int updateByPrimaryKey(UserCount record);
}

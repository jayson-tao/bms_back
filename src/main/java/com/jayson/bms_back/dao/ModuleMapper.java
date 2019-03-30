package com.jayson.bms_back.dao;

import com.jayson.bms_back.pojo.Module;
import com.jayson.bms_back.pojo.ModuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper {
    int countByExample(ModuleExample example);

    int deleteByExample(ModuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Module record);

    int insertSelective(Module record);

    List<Module> selectByExample(ModuleExample example);

    Module selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Module record, @Param("example") ModuleExample example);

    int updateByExample(@Param("record") Module record, @Param("example") ModuleExample example);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

    List<Module> findModulesByUserName(@Param("username") String username);
}

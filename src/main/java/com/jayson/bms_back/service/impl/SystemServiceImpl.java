package com.jayson.bms_back.service.impl;

import com.jayson.bms_back.dao.SystemMapper;
import com.jayson.bms_back.pojo.System;
import com.jayson.bms_back.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemMapper systemMapper;

    /**
     * 记录系统访问量
     */
    @Transactional
    @Override
    public void addNumber() {
        System system = systemMapper.selectByPrimaryKey(1);
        system.setSystemCount(system.getSystemCount()+1);
        systemMapper.updateByPrimaryKeySelective(system);
    }

    @Override
    public Long getNumber() {
        return systemMapper.selectByPrimaryKey(1).getSystemCount();
    }
}

package com.jayson.bms_back.service.impl;

import com.jayson.bms_back.dao.UserCountMapper;
import com.jayson.bms_back.service.UserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCountServiceImpl implements UserCountService {
    @Autowired
    private UserCountMapper userCountMapper;
    @Override
    public Long getNumber() {
        return userCountMapper.selectByPrimaryKey(1).getUserCount();
    }
}

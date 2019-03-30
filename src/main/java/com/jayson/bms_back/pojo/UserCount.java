package com.jayson.bms_back.pojo;

import java.io.Serializable;

public class UserCount implements Serializable {
    private Integer id;

    private Long userCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }
}
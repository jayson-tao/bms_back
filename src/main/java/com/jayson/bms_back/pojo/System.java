package com.jayson.bms_back.pojo;

import java.io.Serializable;

public class System implements Serializable {
    private Integer id;

    private Long systemCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSystemCount() {
        return systemCount;
    }

    public void setSystemCount(Long systemCount) {
        this.systemCount = systemCount;
    }
}
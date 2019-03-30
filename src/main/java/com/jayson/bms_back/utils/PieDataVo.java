package com.jayson.bms_back.utils;

/**
 * 饼状图视图对象
 */
public class PieDataVo {
    String name;
    Integer value;

    public PieDataVo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

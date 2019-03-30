package com.jayson.bms_back.utils;

import java.util.ArrayList;
import java.util.List;

public class PieChartVo<T> {
    private List<Data> list = new ArrayList<>(0);
    private List<String> name = new ArrayList<>(0);

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    public class Data {
        private String name;
        private T value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

}

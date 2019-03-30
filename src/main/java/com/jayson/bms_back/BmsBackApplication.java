package com.jayson.bms_back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jayson.bms_back.dao")
public class BmsBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BmsBackApplication.class, args);
    }

}


package com.jayson.bms_back.controller;

import com.jayson.bms_back.dao.UserCountMapper;
import com.jayson.bms_back.service.BookService;
import com.jayson.bms_back.service.BookTypeService;
import com.jayson.bms_back.service.SystemService;
import com.jayson.bms_back.service.UserCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserCountService userCountService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookTypeService bookTypeService;

    /**
     * 查询系统访问量和用户量
     * @return
     */
    @GetMapping
    public HashMap<String, Long> getNumber(){
        Long systemNumber = systemService.getNumber();
        Long userCount = userCountService.getNumber();
        Long typeNumber = bookTypeService.getNumber();
        Long bookNumber = bookService.getNumber();
        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("system",systemNumber);
        hashMap.put("user",userCount);
        hashMap.put("bookType",typeNumber);
        hashMap.put("book",bookNumber);
        return hashMap;
    }
}

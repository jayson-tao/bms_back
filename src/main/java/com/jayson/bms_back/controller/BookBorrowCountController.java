package com.jayson.bms_back.controller;

import com.jayson.bms_back.service.BookBorrowCountService;
import com.jayson.bms_back.utils.PieChartVo;
import com.jayson.bms_back.utils.PieDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookBorrowCount")
public class BookBorrowCountController {
    @Autowired
    private BookBorrowCountService bookBorrowCountService;
    @GetMapping
    public PieChartVo getData(){
        return  bookBorrowCountService.getData();
    }
}

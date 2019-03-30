package com.jayson.bms_back.service.impl;

import com.jayson.bms_back.dao.BookBorrowCountMapper;
import com.jayson.bms_back.pojo.BookBorrowCount;
import com.jayson.bms_back.service.BookBorrowCountService;
import com.jayson.bms_back.utils.PieChartVo;
import com.jayson.bms_back.utils.PieDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookBorrowCountServiceImpl implements BookBorrowCountService {
    @Autowired
    private BookBorrowCountMapper bookBorrowCountMapper;

    @Override
    public PieChartVo<Long> getData() {
        PieChartVo<Long> list = new PieChartVo<>();
        //数据
        List<PieChartVo<Long>.Data> dataList = list.getList();
        List<String> name = list.getName();
        List<BookBorrowCount> data = bookBorrowCountMapper.getData();
        for (BookBorrowCount borrowCount : data) {
            String bookName = borrowCount.getBook().getName();
            name.add(bookName);
            PieChartVo<Long>.Data data1 = list.new Data();
            data1.setName(bookName);
            data1.setValue(Long.valueOf(borrowCount.getCount()));
            dataList.add(data1);
        }
        return list;
    }
}

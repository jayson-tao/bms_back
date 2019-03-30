package com.jayson.bms_back.service;

import com.jayson.bms_back.pojo.BookBorrow;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;

public interface BorrowBookService {
    Result insert(BookBorrow bookBorrow);

    Pagination page( Integer pageNo, Integer pageSize,Integer state);

    Pagination page( Integer pageNo, Integer pageSize, Integer state,BookBorrow bookBorrow);

    Result updateState(Integer[] ids, Integer state);

    Pagination userPage(Integer userId, Integer pageNo, Integer pageSize, Integer state);

    Result userBack(Integer userId, Integer state, Integer[] ids);

    Result updateBackTime(Integer[] ids);
}

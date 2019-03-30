package com.jayson.bms_back.service;

import com.jayson.bms_back.pojo.BookType;
import com.jayson.bms_back.utils.Result;
import com.jayson.bms_back.utils.ZtreeNodeVo;

import java.sql.SQLException;
import java.util.List;

public interface BookTypeService {
    Result save(BookType bookType) ;



    List<ZtreeNodeVo> findAllTypes();

    Result delete(Integer[] ids);

    BookType getById(Integer id);

    List<BookType> getList();

    Result update(BookType bookType);

    Long getNumber();
}

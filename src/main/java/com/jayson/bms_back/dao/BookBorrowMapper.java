package com.jayson.bms_back.dao;

import com.jayson.bms_back.pojo.BookBorrow;
import com.jayson.bms_back.pojo.BookBorrowExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookBorrowMapper {
    int countByExample(BookBorrowExample example);

    int deleteByExample(BookBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookBorrow record);

    int insertSelective(BookBorrow record);

    List<BookBorrow> selectByExample(BookBorrowExample example);

    BookBorrow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookBorrow record, @Param("example") BookBorrowExample example);

    int updateByExample(@Param("record") BookBorrow record, @Param("example") BookBorrowExample example);

    int updateByPrimaryKeySelective(BookBorrow record);

    int updateByPrimaryKey(BookBorrow record);
}

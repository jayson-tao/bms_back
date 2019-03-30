package com.jayson.bms_back.dao;

import com.jayson.bms_back.pojo.BookBorrowCount;
import com.jayson.bms_back.pojo.BookBorrowCountExample;
import com.jayson.bms_back.utils.PieDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookBorrowCountMapper {
    int countByExample(BookBorrowCountExample example);

    int deleteByExample(BookBorrowCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookBorrowCount record);

    int insertSelective(BookBorrowCount record);

    List<BookBorrowCount> selectByExample(BookBorrowCountExample example);

    BookBorrowCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookBorrowCount record, @Param("example") BookBorrowCountExample example);

    int updateByExample(@Param("record") BookBorrowCount record, @Param("example") BookBorrowCountExample example);

    int updateByPrimaryKeySelective(BookBorrowCount record);

    int updateByPrimaryKey(BookBorrowCount record);

    List<BookBorrowCount>  getData();
}

package com.jayson.bms_back.dao;

import com.jayson.bms_back.pojo.BookType;
import com.jayson.bms_back.pojo.BookTypeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;
import java.util.List;

public interface BookTypeMapper {
    int countByExample(BookTypeExample example);

    int deleteByExample(BookTypeExample example) ;

    int deleteByPrimaryKey(Integer id);

    int insert(BookType record) throws SQLException;

    int insertSelective(BookType record);

    List<BookType> selectByExample(BookTypeExample example);

    BookType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookType record, @Param("example") BookTypeExample example);

    int updateByExample(@Param("record") BookType record, @Param("example") BookTypeExample example);

    int updateByPrimaryKeySelective(BookType record) throws SQLException;

    int updateByPrimaryKey(BookType record);
}

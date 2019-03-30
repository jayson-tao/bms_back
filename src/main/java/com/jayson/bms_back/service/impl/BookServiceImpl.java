package com.jayson.bms_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayson.bms_back.dao.BookMapper;
import com.jayson.bms_back.pojo.Book;
import com.jayson.bms_back.pojo.BookExample;
import com.jayson.bms_back.pojo.UserExample;
import com.jayson.bms_back.service.BookService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 分页查询
     * @param pageNo 当前页
     * @param pageSize 每页的大小
     * @return
     */
    @Override
    public Pagination page(Integer pageNo, Integer pageSize) {
        Pagination pagination = new Pagination();
        BookExample example = new BookExample();
        PageHelper.startPage(pageNo,pageSize);
        List<Book> books = bookMapper.selectByExample(example);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        pagination.setList(books);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) pageInfo.getTotal());
        return pagination;
    }

    @Override
    public Book findById(Integer bookId) {
        return bookMapper.selectByPrimaryKey(bookId);
    }

    @Transactional
    @Override
    public Result update(Book book) {
        Result result = Result.me();
        book.setUpdateTime(System.currentTimeMillis());
        bookMapper.updateByPrimaryKeySelective(book);
        result.setSuccess(true);
        return result;
    }

    @Transactional
    @Override
    public Result delete(Integer[] ids) {
        Result result = Result.me();
        BookExample example = new BookExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        bookMapper.deleteByExample(example);
        result.setMessage("删除成功");
        return result;
    }

    @Override
    public Pagination page(Integer pageNo, Integer pageSize, Book book) {
        Pagination pagination = new Pagination();
        BookExample example = new BookExample();
        BookExample.Criteria criteria = example.createCriteria();
        //图书编号
        Integer id = book.getId();
        //图书名
        String name = book.getName();
        //图书作者
        String author = book.getAuthor();
        //出版社
        String house = book.getPublishHouse();
        //类目
        String type = book.getType();
        if(id!=null){
            criteria.andIdEqualTo(id);
        }else {
            if (name != null && !name.isEmpty()) {
                criteria.andNameLike("%" + name + "%");
            }
            if (author != null && !author.isEmpty()) {
                criteria.andAuthorLike("%" + author + "%");
            }
            if (house != null && !house.isEmpty()) {
                criteria.andPublishHouseLike("%" + house + "%");
            }
            if (type != null && !type.isEmpty()) {
                criteria.andTypeLike("%" + type + "%");
            }
        }
        PageHelper.startPage(pageNo,pageSize);
        List<Book> books = bookMapper.selectByExample(example);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        pagination.setList(books);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) pageInfo.getTotal());
        return pagination;
    }

    @Transactional
    @Override
    public Result addBook(Book book) {
        Result result = Result.me();
        long millis = System.currentTimeMillis();
        book.setCreateTime(millis);
        book.setUpdateTime(millis);
        bookMapper.insert(book);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Long getNumber() {
        BookExample example = new BookExample();
        return Long.valueOf(bookMapper.countByExample(example));
    }
}

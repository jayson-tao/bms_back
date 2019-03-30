package com.jayson.bms_back.service;

import com.jayson.bms_back.pojo.Book;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;

public interface BookService {
    Result addBook(Book book);

    Pagination page(Integer pageNo, Integer pageSize);

    Pagination page(Integer pageNo, Integer pageSize, Book book);

    Result delete(Integer[] ids);

    Result update(Book book);

    Book findById(Integer bookId);

    Long getNumber();
}

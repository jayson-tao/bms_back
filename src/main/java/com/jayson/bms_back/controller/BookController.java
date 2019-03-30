package com.jayson.bms_back.controller;

import com.jayson.bms_back.pojo.Book;
import com.jayson.bms_back.service.BookService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图书
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 添加
     */
    @PostMapping
    public Result addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
    /**
     *
     * @param pageNo 当前页
     * @param pageSize 每页的大小
     */
    @GetMapping("/{pageNo}/{pageSize}")
    public Pagination page(@PathVariable Integer pageNo,
                           @PathVariable Integer pageSize) {
        Pagination pagination ;
        pagination = bookService.page(pageNo, pageSize);
        return pagination;
    }

    /**
     *
     * @param pageNo 当前页
     * @param pageSize 每页的大小
     */
    @PostMapping ("/multiSearch/{pageNo}/{pageSize}")
    public Pagination page(@PathVariable Integer pageNo, @PathVariable Integer pageSize,
        @RequestBody Book book ) {
        return bookService.page(pageNo,pageSize,book);
    }


    /**
     * 删除
     * @param ids id数组
     */
    @DeleteMapping
    public Result delete(@RequestBody Integer ids[]){
        Result result = bookService.delete(ids);
        return result;
    }

    /**
     * 修改
     */
    @PutMapping
    public Result update(@RequestBody Book book){
        return bookService.update(book);
    }

}


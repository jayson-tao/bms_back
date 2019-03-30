package com.jayson.bms_back.controller;

import com.jayson.bms_back.pojo.BookBorrow;
import com.jayson.bms_back.service.BorrowBookService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/borrowBook")
public class BorrowBookController {

    @Autowired
    private BorrowBookService borrowBookService;

    /**
     * 借书
     *
     * @param bookBorrow
     * @return
     */
    @PostMapping
    public Result borrow(@RequestBody BookBorrow bookBorrow) {
        return borrowBookService.insert(bookBorrow);
    }

    /**
     * 分页 借阅记录 1 -》已结借阅
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("record/{pageNo}/{pageSize}/{state}")
    public Pagination page(@PathVariable Integer pageNo,
                           @PathVariable Integer pageSize,
                           @PathVariable Integer state) {
        Pagination pagination;
        pagination = borrowBookService.page(pageNo, pageSize, state);
        return pagination;
    }

    /**
     * 借阅记录 搜索
     *
     * @param pageNo   当前页
     * @param pageSize 每页的大小
     */
    @PostMapping("/multiSearch/{pageNo}/{pageSize}/{state}")
    public Pagination page(@PathVariable Integer pageNo, @PathVariable Integer pageSize,
                           @RequestBody BookBorrow bookBorrow,
                           @PathVariable Integer state) {
        return borrowBookService.page(pageNo, pageSize, state, bookBorrow);
    }

    /**
     * 催还
     *
     * @param ids
     * @return
     */
    @PutMapping("/{state}")
    public Result updateState(@RequestBody Integer[] ids, @PathVariable Integer state) {
        return borrowBookService.updateState(ids, state);
    }

    /**
     * 分页 借阅记录 1 -》已借阅
     *用户的借阅记录
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("record/{userId}/{pageNo}/{pageSize}/{state}")
    public Pagination userPage(@PathVariable Integer pageNo,
                               @PathVariable Integer pageSize,
                               @PathVariable Integer state,
                               @PathVariable Integer userId) {
        Pagination pagination;
        pagination = borrowBookService.userPage(userId,pageNo, pageSize, state);
        return pagination;
    }

    /**
     * 用户归还
     * @param userId
     * @param state
     * @return
     */
    @PutMapping("/{userId}/{state}")
    public Result userBack(@PathVariable Integer userId,
                           @PathVariable Integer state,
                           @RequestBody Integer [] ids){
        return borrowBookService.userBack(userId,state,ids);
    }

    /**
     * 续借
     */
    @PutMapping("/reBorrow")
    public Result reBorrow(
            @RequestBody Integer[] ids){
        return borrowBookService.updateBackTime(ids);
    }
}

package com.jayson.bms_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayson.bms_back.dao.BookBorrowCountMapper;
import com.jayson.bms_back.dao.BookBorrowMapper;
import com.jayson.bms_back.dao.BookMapper;
import com.jayson.bms_back.pojo.*;
import com.jayson.bms_back.service.BookService;
import com.jayson.bms_back.service.BorrowBookService;
import com.jayson.bms_back.service.UserService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.System;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BorrowBookServiceImpl implements BorrowBookService {
    @Autowired
    private BookBorrowMapper bookBorrowMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookBorrowCountMapper bookBorrowCountMapper;

    /**
     * 用户的借阅记录
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param state
     * @return
     */
    @Override
    public Pagination userPage(Integer userId, Integer pageNo, Integer pageSize, Integer state) {
        Pagination pagination = new Pagination();
        BookBorrowExample example = new BookBorrowExample();
        BookBorrowExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (state != null) {
            criteria.andStateEqualTo(state);
        }
        PageHelper.startPage(pageNo, pageSize);
        List<BookBorrow> list = bookBorrowMapper.selectByExample(example);
        PageInfo<BookBorrow> pageInfo = new PageInfo<>(list);
        long totalCount = pageInfo.getTotal();
        pagination.setList(list);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) totalCount);
        return pagination;
    }

    /**
     * 催还
     *
     * @param ids
     * @param state
     * @return
     */
    @Transactional
    @Override
    public Result updateState(Integer[] ids, Integer state) {
        Result result = Result.me();
        BookBorrowExample example = new BookBorrowExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<BookBorrow> bookBorrows = bookBorrowMapper.selectByExample(example);
        for (BookBorrow borrow : bookBorrows) {
            //设置为催还中
            borrow.setState(state);
            bookBorrowMapper.updateByPrimaryKeySelective(borrow);
        }
        result.setSuccess(true);
        result.setMessage("催还成功");
        return result;
    }

    /**
     * 搜索 借阅记录
     *
     * @param pageNo
     * @param pageSize
     * @param bookBorrow
     * @return
     */
    @Override
    public Pagination page(Integer pageNo, Integer pageSize, Integer state, BookBorrow bookBorrow) {
        Pagination pagination = new Pagination();
        BookBorrowExample example = new BookBorrowExample();
        BookBorrowExample.Criteria criteria = example.createCriteria();
        if (state != null) {
            criteria.andStateEqualTo(state);
        }
        String userName = bookBorrow.getUserName();
        String bookName = bookBorrow.getBookName();
        if (userName != null && !userName.isEmpty()) {
            criteria.andUserNameLike("%" + userName + "%");
        }
        if (bookName != null && !bookName.isEmpty()) {
            criteria.andBookNameLike("%" + bookName + "%");
        }
        PageHelper.startPage(pageNo, pageSize);
        List<BookBorrow> list = bookBorrowMapper.selectByExample(example);
        PageInfo<BookBorrow> pageInfo = new PageInfo<>(list);
        long totalCount = pageInfo.getTotal();
        pagination.setList(list);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) totalCount);
        return pagination;
    }

    /**
     * 借阅记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Pagination page(Integer pageNo, Integer pageSize, Integer state) {
        Pagination pagination = new Pagination();
        BookBorrowExample example = new BookBorrowExample();
        if (state != null) {
            example.createCriteria().andStateEqualTo(state);
        }
        PageHelper.startPage(pageNo, pageSize);
        List<BookBorrow> list = bookBorrowMapper.selectByExample(example);
        PageInfo<BookBorrow> pageInfo = new PageInfo<>(list);
        long totalCount = pageInfo.getTotal();
        pagination.setList(list);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) totalCount);
        return pagination;
    }

    @Transactional
    @Override
    public Result insert(BookBorrow bookBorrow) {
        Result result = Result.me();
        User user = userService.findByUserName(bookBorrow.getUserName());
        Book book = bookService.findById(bookBorrow.getBookId());
        //记录该图书的借阅次数
        BookBorrowCountExample example = new BookBorrowCountExample();
        example.createCriteria().andBookIdEqualTo(bookBorrow.getBookId());
        List<BookBorrowCount> bookBorrowCounts = bookBorrowCountMapper.selectByExample(example);
        BookBorrowCount borrowCount;
        //如果有就更新次数
        if (bookBorrowCounts != null && bookBorrowCounts.size() > 0) {
            borrowCount = bookBorrowCounts.get(0);
            borrowCount.setCount(borrowCount.getCount() + 1);
            bookBorrowCountMapper.updateByPrimaryKeySelective(borrowCount);
        } else {
            //没有就插入
            borrowCount = new BookBorrowCount();
            borrowCount.setBookId(bookBorrow.getBookId());
            borrowCount.setCount(1);
        }
        bookBorrowCountMapper.insert(borrowCount);
        if (bookBorrow.getId() == null) {
            if (book.getNumber() < 1) {
                result.setSuccess(false);
                result.setMessage("借阅失败库存不足");
                return result;
            }
            //图书减库存
            book.setNumber(book.getNumber() - 1);
            bookBorrow.setUserId(user.getId());
            bookBorrow.setBookName(book.getName());
            bookBorrow.setBookAuthor(book.getAuthor());
            bookBorrow.setCount(1);
            bookBorrow.setState(1);
            long timeMillis = System.currentTimeMillis();
            bookBorrow.setBorrowTime(timeMillis);
            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.DATE, bookBorrow.getBorrowDays());
            Date time = ca.getTime();
            bookBorrow.setBackTime(time.getTime());
        }
        bookBorrowMapper.insert(bookBorrow);
        bookMapper.updateByPrimaryKeySelective(book);
        result.setSuccess(true);
        result.setMessage("借书成功");
        return result;
    }

    /**
     * 用户归还图书
     *
     * @param userId
     * @param state
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Result userBack(Integer userId, Integer state, Integer[] ids) {
        Result result = Result.me();
        //查询记录
        BookBorrow bookBorrow = bookBorrowMapper.selectByPrimaryKey(ids[0]);
        //设置为已归还
        bookBorrow.setState(3);
        bookBorrow.setRealBackTime(System.currentTimeMillis());
        Integer bookId = bookBorrow.getBookId();
        //归还图书库存
        Book book = bookMapper.selectByPrimaryKey(bookId);
        book.setNumber(book.getNumber() + 1);
        //更新数据库
        bookBorrowMapper.updateByPrimaryKeySelective(bookBorrow);
        bookMapper.updateByPrimaryKeySelective(book);
        result.setSuccess(true);
        return result;
    }

    /**
     * 续借
     *
     * @param ids
     */
    @Transactional
    @Override
    public Result updateBackTime(Integer[] ids) {
        Result result = Result.me();
        BookBorrow bookBorrow = bookBorrowMapper.selectByPrimaryKey(ids[0]);
        if (bookBorrow.getCount() >= 2) {
            result.setSuccess(false);
            result.setMessage("只能续借一次");
            return result;
        }
        bookBorrow.setCount(2);
        //更新归还日期
        Long time = bookBorrow.getBackTime();
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(time);
        c1.add(Calendar.DATE, 7);
        bookBorrow.setBackTime(c1.getTime().getTime());
        bookBorrowMapper.updateByPrimaryKeySelective(bookBorrow);
        result.setSuccess(true);
        return result;
    }
}

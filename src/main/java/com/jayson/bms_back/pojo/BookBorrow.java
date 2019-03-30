package com.jayson.bms_back.pojo;

import java.io.Serializable;

public class BookBorrow implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer bookId;

    private String bookAuthor;

    private String bookName;

    private Integer count;

    private Integer borrowDays;

    private Integer state;

    private Long borrowTime;

    private Long backTime;

    private Long realBackTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(Integer borrowDays) {
        this.borrowDays = borrowDays;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Long borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Long getBackTime() {
        return backTime;
    }

    public void setBackTime(Long backTime) {
        this.backTime = backTime;
    }

    public Long getRealBackTime() {
        return realBackTime;
    }

    public void setRealBackTime(Long realBackTime) {
        this.realBackTime = realBackTime;
    }
}
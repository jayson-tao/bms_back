package com.jayson.bms_back.service;

import com.jayson.bms_back.pojo.Message;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;

public interface MessageService {
    Result addMessage(Message message);

    Pagination page(Integer userId, Integer pageNo, Integer pageSize);

    Result deleteMessage(Integer[] ids);

    Pagination pageAll(Integer pageNo, Integer pageSize);

    Result update(Integer[] ids);
}

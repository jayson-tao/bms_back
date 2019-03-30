package com.jayson.bms_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayson.bms_back.dao.MessageMapper;
import com.jayson.bms_back.pojo.Message;
import com.jayson.bms_back.pojo.MessageExample;
import com.jayson.bms_back.service.MessageService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 添加消息
     * @param message
     * @return
     */
    @Transactional
    @Override
    public Result addMessage(Message message) {
        Result result = Result.me();
        message.setState(0);
        message.setCreatTime(System.currentTimeMillis());
        messageMapper.insert(message);
        result.setSuccess(true);
        return result;
    }

    /**
     * 分页
     * @param userId 用户id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Pagination page(Integer userId, Integer pageNo, Integer pageSize) {
        Pagination pagination = new Pagination();
        MessageExample example = new MessageExample();
        example.createCriteria().andUserIdEqualTo(userId);
        PageHelper.startPage(pageNo,pageSize);
        List<Message> messages = messageMapper.selectByExample(example);
        //获取分页详情
        PageInfo <Message> pageInfo = new PageInfo<>(messages);
        pagination.setList(messages);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) pageInfo.getTotal());
        return pagination;
    }

    /**
     * 删除
     * @param ids id数组
     * @return
     */
    @Transactional
    @Override
    public Result deleteMessage(Integer[] ids) {
        Result result = Result.me();
        MessageExample example = new MessageExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        messageMapper.deleteByExample(example);
        result.setSuccess(true);
        return result;
    }

    /**
     * 留言管理
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Pagination pageAll(Integer pageNo, Integer pageSize) {
        Pagination pagination = new Pagination();
        MessageExample example = new MessageExample();
        PageHelper.startPage(pageNo,pageSize);
        List<Message> messages = messageMapper.selectByExample(example);
        //获取分页详情
        PageInfo <Message> pageInfo = new PageInfo<>(messages);
        pagination.setList(messages);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) pageInfo.getTotal());
        return pagination;
    }
    /**
     * 修改留言状态
     */
    @Override
    public Result update(Integer[] ids) {
        Result result = Result.me();
        MessageExample example = new MessageExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<Message> messages = messageMapper.selectByExample(example);
        for (Message message : messages) {
            message.setState(1);
            messageMapper.updateByPrimaryKeySelective(message);
        }
        result.setSuccess(true);
        return result;
    }
}

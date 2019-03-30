package com.jayson.bms_back.controller;

import com.jayson.bms_back.pojo.Message;
import com.jayson.bms_back.service.MessageService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/message")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 添加
     *
     * @param message
     */
    @PostMapping
    public Result addMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }

    /**
     * 我的留言
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/{pageNo}/{pageSize}")
    public Pagination page(@PathVariable Integer pageNo,
                           @PathVariable Integer pageSize,
                           @PathVariable Integer userId) {

        return messageService.page(userId, pageNo, pageSize);
    }

    @GetMapping("/all/{pageNo}/{pageSize}")
    public Pagination pageAll(
                           @PathVariable Integer pageSize,
                           @PathVariable Integer pageNo) {

        return messageService.pageAll( pageNo, pageSize);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result deleteMessage(@RequestBody Integer[] ids){
        return messageService.deleteMessage(ids);
    }

    /**
     * 修改状态
     */
    @PutMapping
    public Result update(@RequestBody Integer [] ids){
        return messageService.update(ids);
    }
}

package com.jayson.bms_back.controller;

import com.jayson.bms_back.pojo.BookType;
import com.jayson.bms_back.service.BookTypeService;
import com.jayson.bms_back.utils.Result;
import com.jayson.bms_back.utils.ZtreeNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书类目
 */
@RestController
@RequestMapping("/bookType")
public class BookTypeController {
    @Autowired
    private BookTypeService bookTypeService;

    /**
     * 添加类目
     */
    @PostMapping
    public Result add(@RequestBody BookType bookType) {
        return bookTypeService.save(bookType);
    }

    /**
     * 所有条目类别
     */
    @GetMapping
    public List<ZtreeNodeVo> findAllTypes(){

        return bookTypeService.findAllTypes();
    }
    @DeleteMapping
    public Result delete(@RequestBody Integer  [] ids){
        return  bookTypeService.delete(ids);
    }
    /**
     * 查询
     */
    @GetMapping("/{id}")
    public BookType getById(@PathVariable Integer id){
        return bookTypeService.getById(id);
    }

    /**
     * 类目集合list
     */
    @GetMapping("/list")
    public List<BookType> getList(){
        return bookTypeService.getList();
    }

    /**
     * 修改
     */
    @PutMapping
    public Result update(@RequestBody BookType bookType){
        return bookTypeService.update(bookType);
    }

}

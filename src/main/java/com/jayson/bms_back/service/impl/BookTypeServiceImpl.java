package com.jayson.bms_back.service.impl;

import com.jayson.bms_back.dao.BookTypeMapper;
import com.jayson.bms_back.pojo.BookType;
import com.jayson.bms_back.pojo.BookTypeExample;
import com.jayson.bms_back.service.BookTypeService;
import com.jayson.bms_back.utils.Result;
import com.jayson.bms_back.utils.ZtreeNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookTypeServiceImpl implements BookTypeService {
    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Override
    public BookType getById(Integer id) {
        return bookTypeMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public Result update(BookType bookType) {
        Result result = Result.me();
        if(bookType.getLevel()==0){
            bookType.setPid(0);
        }
        bookType.setUpdateTime(System.currentTimeMillis());
        try {
            bookTypeMapper.updateByPrimaryKeySelective(bookType);
            result.setSuccess(true);
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("数据库异常，更新失败");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<BookType> getList() {
        BookTypeExample example = new BookTypeExample();
        return bookTypeMapper.selectByExample(example);
    }

    /**
     * 删除节点
     *
     * @param ids
     */
    @Transactional
    @Override
    public Result delete(Integer[] ids) {
        Result result = Result.me();
        BookTypeExample example = new BookTypeExample();
        example.createCriteria().andPidIn(Arrays.asList(ids));
        List<BookType> types = bookTypeMapper.selectByExample(example);
        if (null != types && types.size() > 0) {
            result.setSuccess(false);
            result.setMessage("该目录下还有子目录，请先删子目录");
        } else {
            example.clear();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            bookTypeMapper.deleteByExample(example);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }
        return result;
    }

    /**
     * 所有类目节点
     */
    @Override
    public List<ZtreeNodeVo> findAllTypes() {
        BookTypeExample example = new BookTypeExample();
        List<BookType> bookTypes = bookTypeMapper.selectByExample(example);
        ArrayList<ZtreeNodeVo> ztreeNodeVos = new ArrayList<>();
        for (BookType type : bookTypes) {
            ZtreeNodeVo nodeVo = new ZtreeNodeVo();
            nodeVo.setId(String.valueOf(type.getId()));
            nodeVo.setName(type.getName());
            nodeVo.setpId(String.valueOf(type.getPid()));
            ztreeNodeVos.add(nodeVo);
        }
        return ztreeNodeVos;
    }


    @Transactional
    @Override
    public Result save(BookType bookType) {
        Result result = Result.me();
        long timeMillis = System.currentTimeMillis();
        bookType.setCreateTime(timeMillis);
        bookType.setUpdateTime(timeMillis);
        if (null == bookType.getPid()) {
            //0表示根节点
            bookType.setPid(0);
        } else {
            //说明是子节点
            BookType parentType = bookTypeMapper.selectByPrimaryKey(bookType.getPid());
            bookType.setLevel(parentType.getLevel() + 1);
        }
        try {
            bookTypeMapper.insert(bookType);
            result.setSuccess(true);
            result.setMessage("添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("数据库异常，请稍后再试");
        }
        return result;
    }

    @Override
    public Long getNumber() {
        BookTypeExample example = new BookTypeExample();
        return Long.valueOf(bookTypeMapper.countByExample(example));
    }
}

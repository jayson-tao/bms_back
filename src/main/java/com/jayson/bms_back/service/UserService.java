package com.jayson.bms_back.service;

import com.jayson.bms_back.pojo.Module;
import com.jayson.bms_back.pojo.User;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
     List<User> validateUserName(String userName);

     Result saveUser(User user) ;

    Pagination page(Integer pageNo, Integer pageSize, Integer type);

    Pagination page(Integer pageNo, Integer pageSize, Integer type, String keyWord);

    Result updateRole(Integer[] ids, Integer type);

    Result delete(Integer[] ids);

    /**
     * 查找当前用户的所有模块
     * @param username 用户名
     */
    List<Module> findModulesByUserName(String username);

    User findByUserName(String username);

    User findUserById(Integer id);


    Result updatePass(Integer id, String oldPass, String newPass);

    void sendSms(String phone, HttpSession session);
}

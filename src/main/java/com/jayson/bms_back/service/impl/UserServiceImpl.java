package com.jayson.bms_back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayson.bms_back.dao.ModuleMapper;
import com.jayson.bms_back.dao.UserCountMapper;
import com.jayson.bms_back.dao.UserMapper;
import com.jayson.bms_back.pojo.Module;
import com.jayson.bms_back.pojo.User;
import com.jayson.bms_back.pojo.UserCount;
import com.jayson.bms_back.pojo.UserExample;
import com.jayson.bms_back.service.UserService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import com.jayson.bms_back.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserMapper userMapper;
    //spring security密码加密器
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ModuleMapper moduleMapper;
    @Autowired
    private UserCountMapper userCountMapper;
    // //短信配置
    //     // @Value("${CLOOPEN_ACCOUNT_SID}")
    //     // private String CLOOPEN_ACCOUNT_SID;
    //     // @Value("${CLOOPEN_ACCOUNT_TOKEN}")
    //     // private String CLOOPEN_ACCOUNT_TOKEN;
    //     // @Value("${CLOOPEN_APP_ID}")
    //     // private String CLOOPEN_APP_ID;
    //     // @Value("${CLOOPEN_SMS_CODE_TEMPALTE_ID}")
    //     // private String CLOOPEN_SMS_CODE_TEMPALTE_ID;

    @Override
    public Pagination page(Integer pageNo, Integer pageSize, Integer type) {
        Pagination pagination = new Pagination();
        UserExample example = new UserExample();
        if (type == 0) {
            //只查读者
            example.createCriteria().andRoleEqualTo(type);
        }
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) pageInfo.getTotal());
        pagination.setList(users);
        return pagination;
    }

    /**
     * 删除读者
     *
     * @param ids id数组
     */
    @Transactional
    @Override
    public Result delete(Integer[] ids) {
        Result result = Result.me();
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        userMapper.deleteByExample(example);
        result.setMessage("删除成功");
        UserCount userCount = userCountMapper.selectByPrimaryKey(1);
        userCount.setUserCount(userCount.getUserCount()-ids.length);
        userCountMapper.updateByPrimaryKeySelective(userCount);
        return result;
    }

    @Transactional
    @Override
    public Result updateRole(Integer[] ids, Integer type) {
        Result result = Result.me();
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<User> users = userMapper.selectByExample(example);
        for (User user : users) {
            if (user.getRole() == 0 && type == 1) {
                //如果是读者才设置为管理员
                user.setRole(type);
                userMapper.updateByPrimaryKeySelective(user);
            } else if (user.getRole() == 1 && type == 0) {
                //如果是读者才设置为管理员
                user.setRole(type);
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        result.setMessage("更新成功");
        return result;
    }

    @Override
    public Pagination page(Integer pageNo, Integer pageSize, Integer type, String keyWord) {
        Pagination pagination = new Pagination();
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (type == 0) {
            //只查读者
            criteria.andRoleEqualTo(type);
        }
        criteria.andUsernameLike("%" + keyWord + "%");
        //查询所有读者
        PageHelper.startPage(pageNo, pageSize);
        List<User> users = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalCount((int) pageInfo.getTotal());
        pagination.setList(users);
        return pagination;
    }

    @Override
    public User findByUserName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        return userMapper.selectByExample(example).get(0);
    }

    /**
     * 查询当前用户的所有模块
     *
     * @param username 用户名
     */
    @Override
    public List<Module> findModulesByUserName(String username) {
        List<Module> modules = moduleMapper.findModulesByUserName(username);
        return modules;
    }

    /**
     * 保存用户
     */
    @Transactional
    @Override
    public Result saveUser(User user) {
        Result result = Result.me();
        //密码加密
        String encodePwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);
        long timeMillis = System.currentTimeMillis();
        user.setCreateTime(timeMillis);
        user.setUpdateTime(timeMillis);
        //初始为读者
        user.setRole(0);
        try {
            userMapper.insert(user);
            result.setMessage("注册成功");
            //记录用户总人数
            UserCount userCount = userCountMapper.selectByPrimaryKey(1);
            userCount.setUserCount(userCount.getUserCount()+1);
            userCountMapper.updateByPrimaryKeySelective(userCount);
        } catch (SQLException e) {
            e.printStackTrace();
            result.setMessage("服务器异常");
        }
        return result;
    }

    /**
     * 根据用户名查询
     */
    @Override
    public List<User> validateUserName(String userName) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(userName);
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPass
     * @param newPass
     * @return
     */
    @Transactional
    @Override
    public Result updatePass(Integer id, String oldPass, String newPass) {
        Result result = Result.me();
        User user = userMapper.selectByPrimaryKey(id);
        String password = user.getPassword();
        //如果旧密码一样才修改密码
        if (passwordEncoder.matches(oldPass,password)) {
            user.setPassword(passwordEncoder.encode(newPass));
            userMapper.updateByPrimaryKeySelective(user);
            result.setSuccess(true);
            return result;
        }
        result.setSuccess(false);
        result.setMessage("密码错误");
        return result;
    }

    @Override
    public void sendSms(String phone, HttpSession session) {

    }
    // @Override
    // public void sendSms(String phone, HttpSession session) {
    //     HashMap<String, Object> result = null;
    //     CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
    //     restAPI.init("app.cloopen.com", "8883");
    //     restAPI.setAccount(CLOOPEN_ACCOUNT_SID, CLOOPEN_ACCOUNT_TOKEN);
    //     restAPI.setAppId(CLOOPEN_APP_ID);
    //     //生成四位随机数
    //     String smsCode = StrUtils.getRandomString(4);
    //     int mintues = 5;
    //     result = restAPI.sendTemplateSMS(phone, CLOOPEN_SMS_CODE_TEMPALTE_ID, new String[]{smsCode, "" + mintues});
    //     System.out.println("SDKTestGetSubAccounts result=" + result);
    //     if ("000000".equals(result.get("statusCode"))) {
    //         // 正常返回输出data包体信息（map）
    //         HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
    //         Set<String> keySet = data.keySet();
    //         for (String key : keySet) {
    //             Object object = data.get(key);
    //             System.out.println(key + " = " + object);
    //         }
    //     } else {
    //         // 异常返回输出错误码和错误信息
    //         System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
    //     }
    //     //发送成功吧验证码保存在session中
    //     session.setAttribute("smsCode", smsCode);
    //     //设置过期时间 ->5分钟
    //     session.setMaxInactiveInterval(300);
    // }
}

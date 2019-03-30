package com.jayson.bms_back.controller;

import com.jayson.bms_back.pojo.User;
import com.jayson.bms_back.service.UserService;
import com.jayson.bms_back.utils.Pagination;
import com.jayson.bms_back.utils.Result;
import com.jayson.bms_back.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //注入一个auth2来清空token信息
    @Autowired
    private ConsumerTokenServices consumerTokenServices;




    /**
     * 查询用户名是否已存在
     */
    @GetMapping("/register/{userName}")
    public Result validateUserName(@PathVariable String userName) {
        Result result = Result.me();
        if (userName.isEmpty()) {
            result.setSuccess(false);
            result.setMessage("用户名不能为空");
            return result;
        } else {
            List<User> users = userService.validateUserName(userName);
            if (users != null && users.size() > 0) {
                //说明用户名已存在
                result.setSuccess(false);
                result.setMessage("用户名已存在");
            } else {
                result.setSuccess(true);
                result.setMessage("用户名可用");
            }
        }
        return result;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @DeleteMapping
    public Result delete(@RequestBody Integer ids[]) {
        Result result = userService.delete(ids);
        return result;
    }

    /**
     * @param pageNo   当前页
     * @param pageSize 每页的大小
     * @param type     用户角色
     * @param keyWord  关键字
     */
    @GetMapping({"/{pageNo}/{pageSize}/{type}", "/{pageNo}/{pageSize}/{type}/{keyWord}"})
    public Pagination page(@PathVariable Integer pageNo,
                           @PathVariable Integer pageSize,
                           @PathVariable Integer type,
                           @PathVariable(required = false) String keyWord) {
        Pagination pagination;
        //如果没有输入模糊查询
        if (null == keyWord || keyWord.isEmpty()) {
            pagination = userService.page(pageNo, pageSize, type);
        } else {
            pagination = userService.page(pageNo, pageSize, type, keyWord);
        }
        return pagination;
    }

    /**
     * 添加管理员
     *
     * @param ids
     * @param type
     */
    @PutMapping("/role/{type}")
    public Result addAdmin(@RequestBody Integer[] ids, @PathVariable Integer type) {
        return userService.updateRole(ids, type);
    }

    /**
     * 返回当前用户的权限
     */
    @GetMapping("/info")
    public Map<String, Object> getInfo(HttpSession session) {
        //这里返回的是我们设置spring-security访问数据定义的MyUserDetailsService
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //这里得到的就是当前用户所有的模块
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> list = new ArrayList<>();
        //authority----对应模块
        for (GrantedAuthority authority : authorities) {
            list.add(authority.getAuthority());
        }
        String username = list.get(1);
        list.remove(1);
        User user = userService.findByUserName(username);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access", list);
        map.put("userId", user.getId());
        map.put("name", user.getName());
        return map;
    }

    /**
     * 退出登录 注销token 清空当前用户认证信息
     */
    @PostMapping("/out")
    public String loginOut(HttpServletRequest request, HttpServletResponse response, String access_token) {
        //注销token
        consumerTokenServices.revokeToken(access_token);
        //获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (access_token != null) {
            //认证信息不为空说明用户已经认证
            //退出认证
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "1";
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    /**
     * 修改密码
     */
    @PutMapping("/{id}")
    public Result updatePass(@RequestBody Params params, @PathVariable Integer id) {
        Result result = Result.me();
        if (params.getNewPass().equals(params.getRePass())) {
            return userService.updatePass(id, params.getOldPass(),params.getNewPass());
        }
        result.setSuccess(false);
        result.setMessage("两次输入的密码不一致");
        return result;
    }

    @GetMapping("/identifyCode/{phone}")
    public void getidentifyCode(@PathVariable String phone, HttpSession session){
        userService.sendSms(phone,session);
    }

}


class Params {
    String oldPass;
    String newPass;
    String rePass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }
}

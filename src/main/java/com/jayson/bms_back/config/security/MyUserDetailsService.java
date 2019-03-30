package com.jayson.bms_back.config.security;


import com.jayson.bms_back.pojo.Module;
import com.jayson.bms_back.service.SystemService;
import com.jayson.bms_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用于spring-security访问数据库
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.jayson.bms_back.pojo.User user = userService.findByUserName(username);
        systemService.addNumber();
        //将 module -> authority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // modules.forEach(module -> {
        //     SimpleGrantedAuthority authorityGranter = new SimpleGrantedAuthority(module.getPermissions());
        //     authorities.add(authorityGranter);
        // });
        //用户的权限
        SimpleGrantedAuthority authorityGranter = new SimpleGrantedAuthority(user.getRole()+"");
        SimpleGrantedAuthority  name = new SimpleGrantedAuthority(user.getUsername());
        authorities.add(authorityGranter);
        authorities.add(name);
        // ValueOperations valueOperations = redisTemplate.opsForValue();
        // valueOperations.set("userId",userId);
        // valueOperations.set("name",name);
        return new User(username, user.getPassword(), authorities);
    }
}

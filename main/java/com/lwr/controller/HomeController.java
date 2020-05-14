package com.lwr.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lwr.entity.User;
import com.lwr.service.UserService;

@Controller
public class HomeController {
    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;

    @RequestMapping("/home")
    public String home(ModelMap map, HttpServletRequest req) {
        req.setAttribute("reqA", "reqsetAttribute");
        map.put("thText", "thText设置文本内容");
        map.put("thUText", "thUText设置文本内容");
        map.put("thValue", "thValue设置当前元素的value值");
        map.put("thEach", Arrays.asList("列表", "遍历列表"));
        map.put("thIf", "msg is not null");
        User user = new User();
        user.setId("ididid");
        user.setName("namelwr");
        map.put("thObject", user);
        return "home/index";
    }
    
    @RequestMapping("/tologin")
    public String tologin() {
        return "login";
    }
    
    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }
    
    /**
     *  /doLogin被绑定到登录filter，如果验证失败直接被拦截进不来
     */
    @RequestMapping("/doLogin")
    public String login(ModelMap map, User user) {
        System.out.println(user.toString());
        map.put("user", user);
        return "home/home";
    }
}

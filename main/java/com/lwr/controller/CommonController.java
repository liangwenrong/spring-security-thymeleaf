package com.lwr.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {
//    @Autowired
//    @Qualifier("userServiceImpl")
//    UserService userService;

    @RequestMapping("/index")
    public String index(ModelMap map, HttpServletRequest req) {
        
        
        return "common/index";
    }

}

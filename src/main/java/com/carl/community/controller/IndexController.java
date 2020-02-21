package com.carl.community.controller;

import com.carl.community.mapper.UserMapper;
import com.carl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaoq
 */
@Controller
public class IndexController {

    private UserMapper userMapper;

    public IndexController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                User user = userMapper.findByToken(cookie.getValue());
                request.getSession().setAttribute("user", user);
            }
        }
        return "index";
    }
}

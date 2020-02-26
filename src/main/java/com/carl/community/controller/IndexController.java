package com.carl.community.controller;

import com.carl.community.dto.QuestionDTO;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.User;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhaoq
 */
@Controller
public class IndexController {

    private UserMapper userMapper;

    private QuestionService questionService;

    public IndexController(UserMapper userMapper, QuestionService questionService) {
        this.userMapper = userMapper;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        // 获取cookie自动登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    User user = userMapper.findByToken(cookie.getValue());
                    request.getSession().setAttribute("user", user);
                }
            }
        }

        List<QuestionDTO> questions = questionService.list();
        model.addAttribute("questions", questions);
        return "index";
    }
}

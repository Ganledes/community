package com.carl.community.controller;

import com.carl.community.dto.PaginationDTO;
import com.carl.community.model.User;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author zhaoq
 * @date 2020/2/28 15:18
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private QuestionService questionService;

    public ProfileController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public String questions(@RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "size", defaultValue = "5") Integer size,
                            Model model,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        assert user != null;
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("action", "questions");
        model.addAttribute("actionName", "我的提问");
        model.addAttribute("pagination", paginationDTO);
        return "profile";
    }

}

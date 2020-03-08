package com.carl.community.controller;

import com.carl.community.dto.PaginationDTO;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.model.User;
import com.carl.community.service.NotificationService;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author zhaoq
 */
@Controller
public class IndexController {

    private QuestionService questionService;

    private NotificationService notificationService;

    public IndexController(QuestionService questionService, NotificationService notificationService) {
        this.questionService = questionService;
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        Model model, HttpSession session) {
        // 分页查询
        PaginationDTO<QuestionDTO> pagination = questionService.list(page, size);
        User user = (User) session.getAttribute("user");
        int notificationCount = 0;
        if (user != null) {
            notificationCount = notificationService.getUnreadCount(user.getId());
        }
        if (notificationCount > 0) {
            session.setAttribute("notificationCount", notificationCount);
        }
        model.addAttribute("pagination", pagination);
        return "index";
    }
}

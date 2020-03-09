package com.carl.community.controller;

import com.carl.community.dto.NotificationDTO;
import com.carl.community.dto.PaginationDTO;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.enums.NotificationType;
import com.carl.community.model.Notification;
import com.carl.community.model.User;
import com.carl.community.service.NotificationService;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private NotificationService notificationService;

    public ProfileController(QuestionService questionService, NotificationService notificationService) {
        this.questionService = questionService;
        this.notificationService = notificationService;
    }

    @GetMapping("/questions")
    public String questions(@RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "size", defaultValue = "5") Integer size,
                            Model model,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        PaginationDTO<QuestionDTO> paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("action", "question");
        model.addAttribute("actionName", "我的提问");
        model.addAttribute("questionPagination", paginationDTO);
        model.addAttribute("pageInfo", paginationDTO);
        return "profile";
    }

    @GetMapping("/notification")
    public String notifications(@RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size,
                                Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PaginationDTO<NotificationDTO> pagination= notificationService.list(user.getId(), page, size);
        int unreadCount = notificationService.getUnreadCount(user.getId());
        session.setAttribute("notificationCount", unreadCount);
        model.addAttribute("action", "notification");
        model.addAttribute("actionName", "最新回复");
        model.addAttribute("notificationPagination", pagination);
        model.addAttribute("pageInfo", pagination);
        return "profile";
    }

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable("id") Long id) {
        NotificationDTO notificationDTO = notificationService.readOne(id);
        if (NotificationType.isReply(notificationDTO.getType())) {
            return  "redirect:/question/" + notificationDTO.getParentId();
        }
        return "redirect:/";
    }
}

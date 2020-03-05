package com.carl.community.controller;

import com.carl.community.dto.QuestionDTO;
import com.carl.community.model.Question;
import com.carl.community.model.User;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaoq
 */
@Controller
public class PublishController {

    private QuestionService questionService;

    public PublishController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question,
                            Model model,
                            HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("user");
        if (obj == null) {
            model.addAttribute("error", "用户未登录");
            model.addAttribute("question", question);
            return "publish";
        }
        if (question.getTitle() == null || question.getTitle().isEmpty()) {
            model.addAttribute("error", "标题不能为空");
        }
        if (question.getDescription() == null || question.getDescription().isEmpty()) {
            model.addAttribute("error", "问题补充不能为空");
        }
        User user = (User) obj;
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}

package com.carl.community.controller;

import com.carl.community.mapper.QuestionMapper;
import com.carl.community.model.Question;
import com.carl.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaoq
 */
@Controller
public class PublishController {

    private QuestionMapper questionMapper;

    public PublishController(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @GetMapping("/publish")
    public String publish() {
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
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}

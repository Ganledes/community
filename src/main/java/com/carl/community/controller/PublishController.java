package com.carl.community.controller;

import com.carl.community.cache.TagCache;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.dto.TagDTO;
import com.carl.community.model.Question;
import com.carl.community.model.User;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public String publish(Model model) {
        List<TagDTO> tagDTOList = TagCache.get();
        model.addAttribute("tagDTOList", tagDTOList);
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<TagDTO> tagDTOList = TagCache.get();
        model.addAttribute("question", questionDTO);
        model.addAttribute("tagDTOList", tagDTOList);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Question question, Model model, HttpServletRequest request) {
        User sessionUser = (User) request.getSession().getAttribute("user");
        model.addAttribute("question", question);
        model.addAttribute("tagDTOList", TagCache.get());
        if (sessionUser == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (StringUtils.isEmpty(question.getTitle())) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(question.getDescription())) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        String errorTag = TagCache.filterInvalid(question.getTag());
        if (!StringUtils.isEmpty(errorTag)) {
            model.addAttribute("error", "输入了非法标签：" + errorTag);
            return "publish";
        }
        question.setCreator(sessionUser.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}

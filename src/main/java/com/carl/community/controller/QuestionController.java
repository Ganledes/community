package com.carl.community.controller;

import com.carl.community.dto.CommentDTO;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.enums.CommentType;
import com.carl.community.model.Question;
import com.carl.community.service.CommentService;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zhaoq
 * @date 2020/2/28 17:29
 */
@Controller
public class QuestionController {

    private QuestionService questionService;

    private CommentService commentService;

    public QuestionController(QuestionService questionService, CommentService commentService) {
        this.questionService = questionService;
        this.commentService = commentService;
    }

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByParentId(id, CommentType.QUESTION);
        // 增加评论数
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionService.incViews(question);
        // 获取相关问题
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

}

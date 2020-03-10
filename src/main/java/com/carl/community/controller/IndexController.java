package com.carl.community.controller;

import com.carl.community.dto.PaginationDTO;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhaoq
 */
@Controller
public class IndexController {

    private QuestionService questionService;

    public IndexController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", defaultValue = "", required = false) String search,
                        Model model) {
        // 分页查询
        PaginationDTO<QuestionDTO> pagination = questionService.list(search, page, size);
        if (!StringUtils.isEmpty(search)) {
            long searchCount = questionService.searchCount(search);
            model.addAttribute("searchCount", searchCount);
        }
        model.addAttribute("search", search);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}

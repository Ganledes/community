package com.carl.community.controller;

import com.alibaba.fastjson.JSON;
import com.carl.community.dto.CommentCreateDTO;
import com.carl.community.dto.CommentDTO;
import com.carl.community.dto.ResultDTO;
import com.carl.community.enums.CommentType;
import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import com.carl.community.model.Comment;
import com.carl.community.model.User;
import com.carl.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zhaoq
 * @date 2020/3/3 16:33
 */
@Controller
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseBody
    @PostMapping("/comment")
    public String comment(@RequestBody CommentCreateDTO commentCreateDTO, HttpSession session) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new CustomizeException(ErrorMessage.NOT_LOGIN);
        }
        comment.setCommenter(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentService.insertSelective(comment);
        return JSON.toJSONString(ResultDTO.ok());
    }

    @ResponseBody
    @GetMapping("/comment/{parentId}")
    public ResultDTO<List<CommentDTO>> getComments(@PathVariable("parentId") Long parentId) {
        List<CommentDTO> comments = commentService.listByParentId(parentId, CommentType.COMMENT);
        return ResultDTO.ok(comments);
    }

}

package com.carl.community.service;

import com.carl.community.dto.CommentDTO;
import com.carl.community.enums.CommentType;
import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import com.carl.community.mapper.CommentMapper;
import com.carl.community.mapper.QuestionMapper;
import com.carl.community.mapper.QuestionMapperExt;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaoq
 * @date 2020/3/4 1:00
 */
@Service
public class CommentService {

    private CommentMapper commentMapper;

    private QuestionMapper questionMapper;

    private UserMapper userMapper;

    private QuestionMapperExt questionMapperExt;

    public CommentService(CommentMapper commentMapper, QuestionMapper questionMapper, UserMapper userMapper, QuestionMapperExt questionMapperExt) {
        this.commentMapper = commentMapper;
        this.questionMapper = questionMapper;
        this.userMapper = userMapper;
        this.questionMapperExt = questionMapperExt;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(Comment comment) {
        if (comment.getParentType() == null || !CommentType.isExist(comment.getParentType())) {
            throw new CustomizeException(ErrorMessage.COMMENT_TYPE_WRONG);
        }
        if (comment.getParentId() == null) {
            throw new CustomizeException(ErrorMessage.TARGET_PARAM_NOT_FOUND);
        }
        if (CommentType.QUESTION.getType().equals(comment.getParentType())) {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(ErrorMessage.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            questionMapperExt.incCommentCount(comment.getParentId());
        } else {
            Comment targetComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (targetComment == null) {
                throw new CustomizeException(ErrorMessage.COMMENT_TARGET_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentTypeEqualTo(CommentType.QUESTION.getType())
                .andParentIdEqualTo(id);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        List<Long> commenterIds = commentList.stream().map(Comment::getCommenter).distinct().collect(Collectors.toList());
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(commenterIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        return commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommenter()));
            return commentDTO;
        }).collect(Collectors.toList());
    }
}
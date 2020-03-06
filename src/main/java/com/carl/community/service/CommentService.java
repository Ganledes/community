package com.carl.community.service;

import com.carl.community.dto.CommentDTO;
import com.carl.community.enums.CommentType;
import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import com.carl.community.mapper.*;
import com.carl.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    private CommentMapperExt commentMapperExt;

    public CommentService(CommentMapper commentMapper, QuestionMapper questionMapper, UserMapper userMapper,
                          QuestionMapperExt questionMapperExt, CommentMapperExt commentMapperExt) {
        this.commentMapper = commentMapper;
        this.questionMapper = questionMapper;
        this.userMapper = userMapper;
        this.questionMapperExt = questionMapperExt;
        this.commentMapperExt = commentMapperExt;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(Comment comment) {
        if (comment.getParentType() == null || !CommentType.isExist(comment.getParentType())) {
            throw new CustomizeException(ErrorMessage.COMMENT_TYPE_WRONG);
        }
        if (comment.getParentId() == null) {
            throw new CustomizeException(ErrorMessage.TARGET_PARAM_NOT_FOUND);
        }
        if (StringUtils.isEmpty(comment.getContent())) {
            throw new CustomizeException(ErrorMessage.COMMENT_IS_EMPTY);
        }
        if (CommentType.QUESTION.getType().equals(comment.getParentType())) {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(ErrorMessage.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            questionMapperExt.incCommentCount(comment.getParentId());
        } else {
            // 回复评论
            Comment targetComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (targetComment == null) {
                throw new CustomizeException(ErrorMessage.COMMENT_TARGET_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapperExt.incCommentCount(parentComment);
        }
    }

    public List<CommentDTO> listByParentId(Long parentId, CommentType type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentTypeEqualTo(type.getType())
                .andParentIdEqualTo(parentId);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (commentList.isEmpty()) {
            return new ArrayList<>();
        }
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
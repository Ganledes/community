package com.carl.community.service;

import com.carl.community.dto.PaginationDTO;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.mapper.QuestionMapper;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.Question;
import com.carl.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoq
 */
@Service
public class QuestionService {

    private QuestionMapper questionMapper;

    private UserMapper userMapper;

    public QuestionService(QuestionMapper questionMapper, UserMapper userMapper) {
        this.questionMapper = questionMapper;
        this.userMapper = userMapper;
    }

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPage(page);
        pagination.setSize(size);
        Integer start = (page - 1) * size;
        List<Question> questionList = questionMapper.list(start, size);
        ArrayList<QuestionDTO> questions = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        pagination.setQuestions(questions);
        Integer totalPage = questionMapper.count();
        pagination.setTotalPage(totalPage);
        pagination.setPagination(page, size, totalPage);
        return pagination;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPage(page);
        pagination.setSize(size);
        Integer start = (page - 1) * size;
        List<Question> questionList = questionMapper.listByUserId(userId, start, size);
        ArrayList<QuestionDTO> questions = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        pagination.setQuestions(questions);
        Integer totalPage = questionMapper.count();
        pagination.setTotalPage(totalPage);
        pagination.setPagination(page, size, totalPage);
        return pagination;
    }

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getById(id);
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.getById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        Question dbQuestion = questionMapper.getById(question.getId());
        question.setGmtModified(System.currentTimeMillis());
        if (dbQuestion == null) {
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.create(question);
        } else {
            question.setCommentCount(dbQuestion.getCommentCount());
            question.setViewCount(dbQuestion.getViewCount());
            questionMapper.update(question);
        }
    }
}

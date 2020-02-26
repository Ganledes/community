package com.carl.community.service;

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

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        ArrayList<QuestionDTO> questions = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        return questions;
    }

}

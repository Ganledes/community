package com.carl.community.service;

import com.carl.community.dto.PaginationDTO;
import com.carl.community.dto.QuestionDTO;
import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import com.carl.community.mapper.QuestionMapper;
import com.carl.community.mapper.QuestionMapperExt;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.Question;
import com.carl.community.model.QuestionExample;
import com.carl.community.model.User;
import org.apache.ibatis.session.RowBounds;
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

    private QuestionMapperExt questionMapperExt;

    private UserMapper userMapper;

    public QuestionService(QuestionMapper questionMapper, QuestionMapperExt questionMapperExt, UserMapper userMapper) {
        this.questionMapper = questionMapper;
        this.questionMapperExt = questionMapperExt;
        this.userMapper = userMapper;
    }

    public PaginationDTO list(Integer page, Integer size) {
        return list(null, page, size);
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO pagination = new PaginationDTO();
        pagination.setPage(page);
        pagination.setSize(size);
        int start = (page - 1) * size;
        QuestionExample questionExample = new QuestionExample();
        if (userId != null) {
            questionExample.createCriteria().andCreatorEqualTo(userId);
        }
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(start, size));
        ArrayList<QuestionDTO> questions = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        pagination.setQuestions(questions);
        Integer totalPage = Math.toIntExact(questionMapper.countByExample(new QuestionExample()));
        pagination.setTotalPage(totalPage);
        pagination.setPagination(page, size, totalPage);
        return pagination;
    }

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(ErrorMessage.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
        question.setGmtModified(System.currentTimeMillis());
        if (dbQuestion == null) {
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.insert(question);
        } else {
            question.setCommentCount(dbQuestion.getCommentCount());
            question.setViewCount(dbQuestion.getViewCount());
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if (updated < 1) {
                throw new CustomizeException(ErrorMessage.SERVER_ERROR);
            }
        }
    }

    public void incViews(Long id) {
        int updated = questionMapperExt.incViews(id);
        if (updated < 1) {
            throw new CustomizeException(ErrorMessage.UPDATE_FAILED);
        }
    }
}

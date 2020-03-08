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
import java.util.stream.Collectors;

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

    public PaginationDTO<QuestionDTO> list(int page, int size) {
        return list(null, page, size);
    }

    public PaginationDTO<QuestionDTO> list(Long userId, int page, int size) {
        PaginationDTO<QuestionDTO> pagination = new PaginationDTO<>();
        pagination.setPage(page);
        pagination.setSize(size);
        int start = (page - 1) * size;
        QuestionExample questionExample = new QuestionExample();
        if (userId != null) {
            questionExample.createCriteria().andCreatorEqualTo(userId);
        }
        questionExample.setOrderByClause("gmt_modified desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(start, size));
        ArrayList<QuestionDTO> questions = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        pagination.setList(questions);
        Integer count = Math.toIntExact(questionMapper.countByExample(new QuestionExample()));
        pagination.setCount(count);
        pagination.setPagination(page, size, count);
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
            questionMapper.insertSelective(question);
        } else {
            question.setCommentCount(dbQuestion.getCommentCount());
            question.setViewCount(dbQuestion.getViewCount());
            questionMapper.updateByPrimaryKeySelective(question);
        }
    }

    public void incViews(Question question) {
        questionMapperExt.incViews(question);
    }

    public List<QuestionDTO> selectRelated(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        String tag = question.getTag().replace(';', '|');
        question.setTag(tag);
        List<Question> relatedQuestionList = questionMapperExt.selectRelated(question);
        return relatedQuestionList.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
    }
}

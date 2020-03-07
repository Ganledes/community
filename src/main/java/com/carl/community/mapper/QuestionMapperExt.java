package com.carl.community.mapper;

import com.carl.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaoq
 */
@Repository
@Mapper
public interface QuestionMapperExt {

    /**
     * 增加问题的浏览数
     * @param question 主键id
     * @return 受影响的行数
     */
    int incViews(Question question);

    int incCommentCount(Question question);

    List<Question> selectRelated(Question question);
}
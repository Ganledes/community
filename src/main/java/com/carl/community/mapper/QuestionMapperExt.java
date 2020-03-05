package com.carl.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhaoq
 */
@Repository
@Mapper
public interface QuestionMapperExt {

    /**
     * 增加问题的浏览数
     * @param id 主键id
     * @return 受影响的行数
     */
    int incViews(Long id);

    int incCommentCount(Long id);

}
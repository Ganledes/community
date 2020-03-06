package com.carl.community.mapper;

import com.carl.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhaoq
 */
@Repository
@Mapper
public interface CommentMapperExt {

    /**
     * 增加评论数
     * @param comment 评论对象
     * @return 数据库受影响的行数
     */
    int incCommentCount(Comment comment);

}
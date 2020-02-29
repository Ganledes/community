package com.carl.community.mapper;

import com.carl.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaoq
 */
@Mapper
@Repository
public interface QuestionMapper {

    /**
     * 新建一个问题
     * @param question 问题
     */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{start}, #{size}")
    List<Question> list(Integer start, Integer size);

    @Select("select count(*) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{start}, #{size}")
    List<Question> listByUserId(Integer userId, Integer start, Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified}," +
            "comment_count=#{commentCount},view_count=#{viewCount},tag=#{tag} where id=#{id}")
    void update(Question question);
}

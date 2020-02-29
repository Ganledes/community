package com.carl.community.mapper;

import com.carl.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author zhaoq
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 添加一个用户
     *
     * @param user 用户
     */
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values " +
            "(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /**
     * 根据token获取用户
     *
     * @param token token
     * @return 返回一个用户对象
     */
    @Select("select * from user where token = #{token} ")
    User getByToken(String token);

    @Select("select * from user where id = #{id}")
    User getById(int id);

    /**
     * 根据accountId获取一个用户对象
     *
     * @param accountId 从GitHub获取的用户id
     * @return 返回一个user对象
     */
    @Select("select * from user where account_id = #{accountId}")
    User getByAccountId(Long accountId);

    @Update("update user set account_id=#{accountId},name=#{name},gmt_modified=#{gmtModified}," +
            "bio=#{bio},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);

    @Select("select token from user where account_id = #{accountId}")
    String getTokenByAccountId(String accountId);
}

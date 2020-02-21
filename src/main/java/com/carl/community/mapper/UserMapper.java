package com.carl.community.mapper;

import com.carl.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author zhaoq
 */
@Mapper
public interface UserMapper {

    /**
     * 添加一个用户
     * @param user 用户
     */
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    /**
     * 根据token获取用户
     * @param token token
     * @return 返回一个用户对象
     */
    @Select("select * from user where token = #{token} ")
    User findByToken(String token);
}

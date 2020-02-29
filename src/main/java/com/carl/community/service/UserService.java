package com.carl.community.service;

import com.carl.community.mapper.UserMapper;
import com.carl.community.model.User;
import org.springframework.stereotype.Service;

/**
 * @author zhaoq
 * @date 2020/2/29 13:53
 */
@Service
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void insertOrUpdate(User user) {
        User dbUser = userMapper.getByAccountId(Long.valueOf(user.getAccountId()));
        if (dbUser != null) {
            update(user);
        } else {
            userMapper.insert(user);
        }
    }

    public void update(User user) {
        user.setGmtModified(System.currentTimeMillis());
        userMapper.update(user);
    }

    public String getTokenByAccountId(String accountId) {
        return userMapper.getTokenByAccountId(accountId);
    }
}

package com.carl.community.service;

import com.carl.community.mapper.UserMapper;
import com.carl.community.model.User;
import com.carl.community.model.UserExample;
import org.springframework.stereotype.Service;

import java.util.List;

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
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            user.setId(users.get(0).getId());
            update(user);
        }
    }

    public void update(User user) {
        user.setGmtModified(System.currentTimeMillis());
        userMapper.updateByPrimaryKeySelective(user);
    }

    public String getTokenByAccountId(String accountId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0).getToken();
    }
}

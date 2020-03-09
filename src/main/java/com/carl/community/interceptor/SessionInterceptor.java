package com.carl.community.interceptor;

import com.carl.community.exception.CustomizeException;
import com.carl.community.exception.ErrorMessage;
import com.carl.community.mapper.UserMapper;
import com.carl.community.model.User;
import com.carl.community.model.UserExample;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhaoq
 * @date 2020/2/28 17:00
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private UserMapper userMapper;

    public SessionInterceptor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 1) {
                        throw new CustomizeException(ErrorMessage.SERVER_ERROR);
                    }
                    request.getSession().setAttribute("user", users.get(0));
                    break;
                }
            }
        }
        return true;
    }
}
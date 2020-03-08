package com.carl.community.interceptor;

import com.carl.community.model.User;
import com.carl.community.service.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaoq
 * @date 2020/3/9 0:49
 */
@Component
public class NotificationInterceptor implements HandlerInterceptor {

    private NotificationService notificationService;

    public NotificationInterceptor(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            int unreadCount = notificationService.getUnreadCount(user.getId());
            request.getSession().setAttribute("notificationCount", unreadCount);
        }
        return true;
    }
}

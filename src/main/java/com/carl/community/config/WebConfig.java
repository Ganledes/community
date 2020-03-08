package com.carl.community.config;

import com.carl.community.interceptor.NotificationInterceptor;
import com.carl.community.interceptor.SessionInterceptor;
import com.carl.community.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhaoq
 * @date 2020/2/28 16:57
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private SessionInterceptor sessionInterceptor;

    private UserInterceptor userInterceptor;

    private NotificationInterceptor notificationInterceptor;

    public WebConfig(SessionInterceptor sessionInterceptor, UserInterceptor userInterceptor,
                     NotificationInterceptor notificationInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
        this.userInterceptor = userInterceptor;
        this.notificationInterceptor = notificationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor);
        registry.addInterceptor(userInterceptor).addPathPatterns("/profile/**");
        registry.addInterceptor(notificationInterceptor).addPathPatterns("/**");
    }
}

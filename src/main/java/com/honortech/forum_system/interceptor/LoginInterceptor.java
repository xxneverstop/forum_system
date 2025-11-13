package com.honortech.forum_system.interceptor;

import com.honortech.forum_system.config.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 登陆拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${honortech.login.url}")
    private String defaultURL;

    /**
     * 前置处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // 校验通过返回真
        if (session != null &&  session.getAttribute(AppConfig.USER_SESSION) != null) {
            return true;
        }
        // 校验url
        if ( !defaultURL.startsWith("/")) {
            defaultURL = "/" +  defaultURL;
        }
        // 校验不通过，跳转到登录界面
        response.sendRedirect(defaultURL);
        return false;
    }
}

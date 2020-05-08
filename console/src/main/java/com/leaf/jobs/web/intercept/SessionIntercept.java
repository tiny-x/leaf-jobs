package com.leaf.jobs.web.intercept;

import com.leaf.jobs.web.SessionAttribute;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * session
 */
public class SessionIntercept implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionAttribute.CURRENT_USER.name()) != null) {
            return true;
        }
        response.sendRedirect("/login.html");
        return false;
    }
}

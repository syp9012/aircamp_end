package com.ac.aircamp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class SessionCheckInter implements HandlerInterceptor {
    // preHandle(request,response,handler)메소드
    // 1.Controller에서 요청을 받기 전에  preHandle()가 호출되어 가로채는 역할로 사용
    // 2.로그인 하지않고(세션이 없으면) 요청하면 로그인 폼으로 이동 하도록 해주는 역할
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("u_id");
        // 세션이 없을때
        if (id == null || id.isEmpty())  {
            response.sendRedirect("user_login.do");	// 세션이 없으면 로그인 폼으로 이동
            return false;
        // 세션이 있을때
        }
        return true;
    }
}


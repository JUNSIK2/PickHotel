package com.example.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Configuration
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    //인증에 실패한 경우 예외처리
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

//        boolean isRemberMe = false;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("remember-me")) {
//                    isRemberMe = true;
//                    break;
//                }
//            }
//        }
//        if (isRemberMe) {
//            response.sendRedirect("/denine");
//        } else {
//            log.error("AuthenticationException : ", authException);
//            response.sendRedirect("/login");
//        }
        response.sendRedirect("/login");
    }
}
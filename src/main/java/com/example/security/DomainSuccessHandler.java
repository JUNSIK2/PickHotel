package com.example.security;


import com.example.member.model.UserAccount;
import com.example.member.persistent.RoleResourceDao;
import com.example.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class DomainSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    RoleResourceDao resourceDao;

    @Autowired
    MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 유저 성공 로직을 추가 해준다.
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("call successHandler");

        // 로그인 방문수 카운트
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount member = (UserAccount) authentication.getPrincipal();
        int memNo = member.getMember().getMemNo();

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        //request에서 넘어온 쿠키가 있는 경우
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginCount")) {
                    oldCookie = cookie;
                }
            }
        }
        if (oldCookie != null) {
            log.info("oldCookie:{}", oldCookie.getValue());

            if (!oldCookie.getValue().contains("[" + memNo + "]")) {
                this.memberService.reviseVisitCount(memNo);
                oldCookie.setValue(oldCookie.getValue() + "_[" + memNo + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            this.memberService.reviseVisitCount(memNo);
            Cookie newCookie = new Cookie("loginCount", "[" + memNo + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }

        //소진 - 로그인후 페이지 처리 - https://codevang.tistory.com/269
        // 디폴트 URI
        String uri = "/";

        /* 강제 인터셉트 당했을 경우의 데이터 get */
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        /* 로그인 버튼 눌러 접속했을 경우의 데이터 get */
        String prevPage = (String) request.getSession().getAttribute("prevPage");

        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage");
        }

        // null이 아니라면 강제 인터셉트 당했다는 것
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();

            // ""가 아니라면 직접 로그인 페이지로 접속한 것
        } else if (prevPage != null && !prevPage.equals("")) {
            uri = prevPage;
        }

        // 세 가지 케이스에 따른 URI 주소로 리다이렉트
        response.sendRedirect(uri);
    }

}
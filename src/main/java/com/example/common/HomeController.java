package com.example.common;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    //메인 페이지
    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "page/index";
    }

    //권한 없음 페이지
    @GetMapping(value = {"/denine"})
    public String denine() {
        return "error/denine";
    }
}

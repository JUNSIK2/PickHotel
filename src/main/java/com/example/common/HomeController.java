package com.example.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

//xss테스트 경로
//    @ResponseBody
//    @GetMapping(value = "/responseXss")
//    Map<String, Object> responseXss() {
//        Map<String, Object> resultMap = new HashMap<>();
//
//        resultMap.put("htmlTdTag", "<td></td>");
//        resultMap.put("htmlTableTag", "<table>");
//
//        return resultMap;
//    }
}

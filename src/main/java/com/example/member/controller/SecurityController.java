package com.example.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

    // 로그인 GET
    @GetMapping("/login")
    public String login(@RequestParam(value = "prevPage", required = false) String prevPage, HttpServletRequest request, String message, Model model) {

        // 요청 시점의 사용자 URI 정보를 Session의 Attribute에 담아서 전달(잘 지워줘야 함)
        // 로그인이 틀려서 다시 하면 요청 시점의 URI가 로그인 페이지가 되므로 조건문 설정
        String uri = request.getHeader("Referer");
        String goBack = "";
        if (uri != null) {
            if (!uri.contains("/login")) {
                if (prevPage != null && prevPage != "") {
                    goBack = prevPage;
                } else {
                    goBack = request.getHeader("Referer");
                }
                request.getSession().setAttribute("prevPage", goBack);
            }
        }

        model.addAttribute("message", message);
        return "page/member_login";
    }

    // 로그인 Post
    @PostMapping("/login")
    public String login2() {
        return "page/member_login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        return "/";
    }


//    @GetMapping("/loginSuccess")
//    @ResponseBody
//    public String loginSuccess(){
//
//        String pwd = passwordEncoder.encode("1111");
//        System.out.println("pwd"+pwd);
//
//        return "로그인에 성공하셨습니다.";
//    }
//
//    @GetMapping("/loginFail")
//    @ResponseBody
//    public String loginfail(){
//
//        String pwd = passwordEncoder.encode("1111");
//        System.out.println("pwd"+pwd);
//
//        return "로그인에 실패하셨습니다.";
//    }

//    /*권한 test*/
//    @GetMapping("/intranet/1")
//    @ResponseBody
//    public String admin(){
//        return "admin 페이지입니다.";
//    }

//    @GetMapping("/member/1")
//    @ResponseBody
//    public String member(){
//        return "member 페이지입니다.";
//    }
//
//    @GetMapping("board/5")
//    @ResponseBody
//    public String grade5(){
//        return "grade5 페이지입니다.";
//    }
//
//    @GetMapping("board/4")
//    @ResponseBody
//    public String grade4(){
//        return "grade4 페이지입니다.";
//    }
//
//    @GetMapping("board/3")
//    @ResponseBody
//    public String grade3(){
//        return "grade3 페이지입니다.";
//    }
//
//    @GetMapping("board/2")
//    @ResponseBody
//    public String grade2(){
//        return "grade2 페이지입니다.";
//    }
//
//    @GetMapping("board/1")
//    @ResponseBody
//    public String grade1(){
//        return "grade1 페이지입니다.";
//    }

}

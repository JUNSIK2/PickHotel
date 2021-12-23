//package com.example.util;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.util.PatternMatchUtils;
//
//import com.example.vo.MemberVo;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class LoginCheckFilter implements Filter {
//	// 관리자 페이지
//	private static final String[] intranet = { "/intranet*", "/intranet/*" };
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		String requestURI = httpRequest.getRequestURI();
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//		try {
//			if (isLoginCheckPath(requestURI)) {
//				HttpSession session = httpRequest.getSession(false);
//				if (session == null || session.getAttribute("member") == null) {
//					log.info("미인증 사용자 요청 {}", requestURI);
//					// 로그인으로 redirect
//					httpResponse.sendRedirect("/member_loginform?redirectURL=" + requestURI);
//					return; // 여기가 중요, 미인증 사용자는 다음으로 진행하지 않고 끝!
//				}else {
//					// 로그인 세션에 저장된 회원 정보 가져오기
//					int grade = ((MemberVo) session.getAttribute("member")).getGrade();
//					if (grade != 5 ) {
//						log.info("관리자 아님");
//						httpResponse.sendRedirect("/denine");
//						return;
//					}
//				}
//			}
//			chain.doFilter(request, response); // 다음 필터 진행. 없다면 서블릿 띄우기
//		} catch (Exception e) {
//			throw e; // 예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함
//		} finally {
//			log.info("인증 체크 필터 종료 {}", requestURI);
//		}
//	}
//
//	private boolean isLoginCheckPath(String requestURI) {
//		//관리자 페이지 접근시 검사
//		return PatternMatchUtils.simpleMatch(intranet, requestURI);
//	}
//}
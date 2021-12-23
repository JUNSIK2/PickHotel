//package com.example.config;
//
//import javax.servlet.Filter;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.example.util.LoginCheckFilter;
//
//@Configuration
//public class WebConfig {
//	@Bean
// 	public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//	 	filterRegistrationBean.setFilter(new LoginCheckFilter()); //내가 구현한 필터 넣기
// 	 	filterRegistrationBean.setOrder(1); //필터 체인할 때 가장 먼저 실행
// 		 filterRegistrationBean.addUrlPatterns("/*"); //모든 요청 url에 대해 실행
// 	 	return filterRegistrationBean;
// 	}
//}
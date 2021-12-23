package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.service.MemberService;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MemberService memberService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/member/**").authenticated();
		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		http.authorizeRequests().antMatchers("/intranet/**").hasRole("ADMIN");

		//특정 주소 권한 해제
		http.csrf().ignoringAntMatchers("/intranet/member/block_cancel")
		.ignoringAntMatchers("/intranet/member/grade_modify")
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		
		http.formLogin().loginPage("/login").defaultSuccessUrl("/", true);
		http.exceptionHandling().accessDeniedPage("/denine");
		http.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/");

	}

//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		log.info("build Auth global...");
//		auth.inMemoryAuthentication().withUser("manager").password("{noop}12345678").roles("MANAGER");
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}12345678").roles("ADMIN");
//	}

}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
public class PickHotel2Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PickHotel2Application.class, args);
		DispatcherServlet dispatcherServlet = (DispatcherServlet)ctx.getBean("dispatcherServlet");
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}

}

package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(value =  {"/", "/index"})
	public String index() {
		return "page/index";
	}
	
//	@GetMapping("/denine")
//	public String noPermission() {
//		return "/error/denine";
//	}
}

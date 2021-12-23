package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.service.FileUploadService;
import com.example.service.PostService;

@Controller
public class FileController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) {
		return "";
	}
}


















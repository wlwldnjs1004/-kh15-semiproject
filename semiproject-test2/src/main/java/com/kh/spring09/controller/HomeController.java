package com.kh.spring09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//메인페이지 및 공용 페이지 작성을 위한 컨트롤러 (고용주소를 부여하지 않음)
@Controller

public class HomeController {

	@RequestMapping("/")
	public String home() {
		return"/WEB-INF/views/home.jsp";
	}
	
}

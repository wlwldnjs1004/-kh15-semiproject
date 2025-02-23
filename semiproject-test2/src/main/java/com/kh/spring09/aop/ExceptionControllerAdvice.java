package com.kh.spring09.aop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kh.spring09.error.NoPermissionException;
import com.kh.spring09.error.TargetNotFoundException;

@ControllerAdvice(annotations = {Controller.class})
public class ExceptionControllerAdvice {
//@
	//해당 예외 발생 시 지정한 작업이 자동으로 실행된다
	//매개변수에 예외 객체를 선언하면 해당 예외 정보가 자동으로 들어온다
	
	@ExceptionHandler(NoPermissionException.class)
	public String noPermission(NoPermissionException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "/WEB-INF/views/error/no-permission.jsp";
	}
//@ExceptionHandler(TargetNotFoundException.class)
//public ResponseEntity<String> notFound(TargetNotFoundException e, Model model){
//
//	model.addAttribute("message",e.getMessage());
//	//	return ResponseEntity.notFound().build();
//	return ResponseEntity.status(404).build();
//}

	@ExceptionHandler(TargetNotFoundException.class)
	public String notFound(TargetNotFoundException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "/WEB-INF/views/error/notfound.jsp";
	}
@ExceptionHandler(Exception.class)
public String error(Exception e,Model model) {
	e.printStackTrace();
	model.addAttribute("message",e.getMessage());
	return"/WEB-INF/views/error/etc.jsp";
}

}




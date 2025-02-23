package com.kh.spring09.aop;



import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring09.error.NoPermissionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Service
public class AdminLoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//목표 - 비회원이나 관리자가 아닌 회원이 통과하는 것을 차단
		HttpSession session = request.getSession();
		String userLevel = (String)session.getAttribute("userLevel");
		if(userLevel == null) {//비회원
			throw new NoPermissionException("로그인 필요");
		}
		if(userLevel.equals("관리자") == false) {
			throw new NoPermissionException("권한 부족");
		}
		
		return true;//나머지는 통과
	}
	
}

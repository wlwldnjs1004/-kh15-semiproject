package com.kh.spring09.aop;



import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring09.error.NoPermissionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


//회원만 통과하도록 검사하는 인터셉터


@Service
public class MemberLoginlnterceptor implements HandlerInterceptor {
 @Override
public boolean preHandle(HttpServletRequest request, 
					HttpServletResponse response, 
					Object handler)
		throws Exception {
	 
	 //로그인 되어 있다=HttpSession에 userId라는 이름의 값이 존재 한다
	 //세션만 조사하면 되는데 세션이 없네? 어떤식으로든 구해온다
	 
	 //-HttpServletRequest는  사용자의 정보가 담겨 있다
	 //-HttpServletResponse는 사용자에게 보낼정보가 담겨 있다
	 //-handler는 실행할 컨트롤러의 정보가 담겨 있다(사용하지 않음)

	 HttpSession session=request.getSession();
	 String userId=(String) session.getAttribute("userId");
	 
	 if(userId !=null) {//로그인이 되어 있다면
		 return true;
	 }
//	 else {
		 //1.로그인 페이지로 리다이렉트
//		 response.sendRedirect("/member/login");
//		 return false;//차단
		 
		 
		 //2.HTTP 상대코드 반환(401)
//		 response.sendError(401);
//		 return false;//차단
		 
		 //3.커스텀 예외처리
		 throw new NoPermissionException("로그인 후 이용 가능합니다");

	
 
 
}
}
	

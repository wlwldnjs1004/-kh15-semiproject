package com.kh.spring09.aop;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//인터셉터(Interceptor
//-지정한 주소에 대해서 특정 시점에 개입할 수 있는 도구
//-정해진 역할과 코드가 있으므로 상속을 통해 구현(HandIerInterceptor
//등록을 수행(@Service,복잡한 작업을 한번에 )
//-특정 매소드르 재정의 하여 코드를 구현
//-preHandIe-컨트롤러 실행 이전 시점에 간섭하는 메소드
//-postHandIe- 컨트롤러 완료 시점에 간섭하는 메소드
//-afterCompletion - 화면 생성 완료 시점에 간섭 하는 메소드


@Service
public class TestLoglnterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("TestLogInterceptor 실행!");
		return true;//작업 혀용
//		return false;//작업중지 차단
	
	}	
@Override
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
	System.out.println("Handler="+handler);
	System.out.println("MAV"+modelAndView);
	
	
	
}
@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {

	System.out.println("handler="+handler);
	System.out.println("exception="+ex);
	
}

}

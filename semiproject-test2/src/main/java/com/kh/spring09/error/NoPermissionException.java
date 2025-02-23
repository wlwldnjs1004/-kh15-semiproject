package com.kh.spring09.error;


//내가 만든 예외 클래스
//- 자바에서 정의 하지 않은 상황을 내가 필요에 의해서 정의하여 사용
//-단, Exception을 상속받아야 한다
//-예외처리를 선택적으로 하고 싶다면 RuntimeException을 상속 받아도 된다


public class NoPermissionException extends RuntimeException{
	public NoPermissionException(String message) {
		super(message);
	}	
	
}

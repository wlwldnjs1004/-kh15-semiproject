package com.kh.spring09.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring09.dao.BoardDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 조회 수 증가 처리
 * -차단용 인터셉터가 아니라 전처리(사전작업)용 인터셉터로 구현
 */


/**
조회 수 증가 처리
- 차단용 인터셉터가 아니라 전처리(사전작업)용 인터셉터로 구현
*/
@Service
public class BoardReadInterceptor implements HandlerInterceptor {
@Autowired
private BoardDao boardDao;
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	//사전에 진행하고 싶은 작업을 코드로 구현
	int boardNo = Integer.parseInt(request.getParameter("boardNo"));
	boardDao.updateBoardRead(boardNo);
	return true;//무조건 통과
}
}
package com.kh.spring09.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring09.dao.BoardDao;
import com.kh.spring09.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 조회 수 증가 처리
 * 
 * 
 * 
 * -차단용 인터셉터가 아니라 전처리(사전작업)용 인터셉터로 구현
 *
 * 조회수 증가를 하지 않아야 할 상황
 *1.자ㅣㄱ자신의 글을 읽는 경우는 조회 수 증가를 하지 않는다
 *
 *
 */


@Service
public class BoardReadInterceptor2 implements HandlerInterceptor{

	@Autowired
	private BoardDao boardDao;
	
	@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	//사전에 진행하고 싶은 작업을 코드로 구현
	int boardNo=Integer.parseInt(request.getParameter("boardNo"));

	//차단하고 싶은 상황을 검사하여 제거 처리
	HttpSession session=request.getSession();
	String userId=(String)session.getAttribute("userId");
	BoardDto boardDto= boardDao.selectOne(boardNo);
	if(userId==null) {//비회원이면
		return true;//조회수 증가하지 말고 그냥 가세요
	}
	
	
	if(userId.equals(boardDto.getBoardWriter())) {//작성자면
		return true;//조회수 증가하지말고 그냐 가세요
	}
	
	
	boardDao.updateBoardRead(boardNo);
	return true;//무조건 통과
	}

	}

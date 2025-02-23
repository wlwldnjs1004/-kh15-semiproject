package com.kh.spring09.aop;

import java.util.HashSet;

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
 *1.자기자신의 글을 읽는 경우는 조회 수 증가를 하지 않는다
 *2.같은 세션당 한 번만 조회수가 증가되도록 처리
 *
 *-세션에 현재 사용자가 읽은 글 번호를 저장할 저장소를 설정
 *
 *
 */




@Service
public class BoardReadInterceptor3 implements HandlerInterceptor{

	@Autowired
	private BoardDao boardDao;
	
	@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
	//사전에 진행하고 싶은 작업을 코드로 구현
	int boardNo=Integer.parseInt(request.getParameter("boardNo"));

	//차단하고 싶은 상황을 검사하여 제거 처리
	//1.작성자는 무조건 조회수 증가 금지
	HttpSession session=request.getSession();
	String userId=(String)session.getAttribute("userId");
	BoardDto boardDto= boardDao.selectOne(boardNo);
	if(userId !=null&&userId.equals(boardDto.getBoardWriter())) {//작성자면
		return true;//조회수 증가하지말고 그냥 가세요
	}
	
	//2.세션을 이용하여 읽은 글인지 확인
	//-세션에 history라는 이름으로 저장소를 만들어둘 계획
	//-단, 미리 만들어놨을 수도 있음

	HashSet<Integer> history=(HashSet<Integer>)session.getAttribute("history");
	if(history==null) {
		history=new HashSet<>();
	}
	
	System.out.println("history"+history);
	
	if(history.contains(boardNo)) {//이미 읽은적 있는 글번호라면
		return true;//조회 수 증가 없이 그냥 통과
	}
	else {//읽은적이 없는 글번호라면
		boardDao.updateBoardRead(boardNo);//조회수 증가처리
		history.add(boardNo);//저장소에 번호를 추가하고
		session.setAttribute("history", history);//세션을 갱신시키고
		
		
		
	}
	
	return true;//무조건 통과
	}

	}

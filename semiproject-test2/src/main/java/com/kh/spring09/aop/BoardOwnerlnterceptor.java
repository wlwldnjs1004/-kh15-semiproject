package com.kh.spring09.aop;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring09.dao.BoardDao;
import com.kh.spring09.dto.BoardDto;
import com.kh.spring09.error.NoPermissionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//목표: 본인 소유의 글에대한 접근만 허용, 나머지는 차단(NoPermissionException 발생)
@Service
public class BoardOwnerlnterceptor implements HandlerInterceptor{

	
	@Autowired
	private BoardDao boardDao;
	
	@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
HttpSession session=request.getSession();
String userId=(String)session.getAttribute("userId");
		if(userId==null) {
			throw new NoPermissionException("로그인이 필요합니다");
		}
	
		//[2]게시글의 작성자를 확인
		//[2-1]글번호 추출(컨트롤러에서는 @RequestParam 하면 끝나지만..)
		//-필요하다면 파스(Integer,parseInt()를 사용)
		int boardNo= Integer.parseInt(request.getParameter("boardNo"));
		//[2-2]글정보를 조회

		
		
		BoardDto boardDto = boardDao.selectOne(boardNo);
		if(boardDto==null) {
			throw new NoPermissionException("존재하지 않는 글입니다");
			
		}
		//[2-3]작성자가 없으면 차단
		if(boardDto.getBoardWriter()==null) {
			throw new NoPermissionException("탈퇴한 사용자의 글입니다");
		}
		
		//모든 단계를 통과 했다면 작성자가 같은지 판단 해서 승인 처리
		if(userId.equals(boardDto.getBoardWriter())) {
	return true;
		}


		throw new NoPermissionException("글을 수정할 권한이 없습니다");
			}
		}
	

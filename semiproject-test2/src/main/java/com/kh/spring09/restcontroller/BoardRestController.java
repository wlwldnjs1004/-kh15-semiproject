package com.kh.spring09.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring09.Service.AttachmentService;
import com.kh.spring09.dao.BoardDao;
import com.kh.spring09.error.TargetNotFoundException;

import jakarta.servlet.http.HttpSession;


@CrossOrigin
@RestController
@RequestMapping("/rest/board")
public class BoardRestController {

	
	@Autowired
	private  BoardDao boardDao;
	
	
	
	//좋아요 여부 검사 매핑
		@RequestMapping("/check")
		public Map<String, Object> check(@RequestParam int boardNo, HttpSession session) {
		String userId=(String)session.getAttribute("userId");
		boolean done= boardDao.checkBoardLike(userId,boardNo);
		int count=boardDao.countBoardLike(boardNo);
		boardDao.updateBoardLike(boardNo, count);//좋아요 개수 갱신

		Map<String, Object>map=new HashMap<>();
		map.put("done", done);//이 회원의 좋아요 여부
		map.put("count", count);//이 글의 좋아요 개수
		
		return map;
		
		
	}
	
	//좋아요 설정/해제 매핑
	@RequestMapping("/action")
	public Map<String, Object> action(@RequestParam  int boardNo, 
			HttpSession session){
		String userId=(String)session.getAttribute("userId");
		boolean done=boardDao.checkBoardLike(userId, boardNo);
		if(done) {//이미 좋아요 한 상태
			boardDao.deleteBoardLike(userId, boardNo);//삭제
		}
		else {//좋아요 한 적 없는 상태
			boardDao.insertBoardLike(userId, boardNo);
		
	
		}
		int count=boardDao.countBoardLike(boardNo);//카운트 다시 구하기
				
				Map<String, Object>map=new HashMap<>();
				map.put("done",!done);//이 회원의 좋아요 여부
				
				map.put("count", count);//이 글의 좋아요 개수
				return map;
				
}
	
	//첨부파일 업로드(summernote 전용 기능)
	@Autowired
	private AttachmentService attachmentService;
	
	@PostMapping("/upload")
	public int upload(@RequestParam MultipartFile attach) throws IllegalStateException, IOException{
		if(attach.isEmpty()) {
			throw new TargetNotFoundException("첨부 파일이 없습니다");
			
		}
		return attachmentService.save(attach);
	}
	@PostMapping("/uploads")
	public List<Integer> uploads(
			@RequestParam (value="attch")List<MultipartFile> attachList) throws IllegalStateException, IOException{
	List<Integer> numbers=new ArrayList<>();	
	for(MultipartFile attach:attachList) {

		if(attach.isEmpty())continue;
		int attachmentNo=attachmentService.save(attach);
		numbers.add(attachmentNo);
		
		
		
	}
	return numbers;
	
	}	
}

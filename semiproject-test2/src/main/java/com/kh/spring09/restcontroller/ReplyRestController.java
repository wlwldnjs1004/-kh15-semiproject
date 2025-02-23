package com.kh.spring09.restcontroller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring09.dao.BoardDao;
import com.kh.spring09.dao.ReplyDao;
import com.kh.spring09.dto.ReplyDto;

import jakarta.servlet.http.HttpSession;

//@CrossOrigin//같은 프로젝트이므로 사용할 필요가 없음
@RestController
@RequestMapping("/rest/reply")
public class ReplyRestController {
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private BoardDao boardDao;
	
	//댓글 목록
	@RequestMapping("/list")
	public List<ReplyDto> list(@RequestParam int replyOrigin) {
		return replyDao.selectList(replyOrigin);
	}
	
	//댓글 작성
	@PostMapping("/write")
	public void write(@ModelAttribute ReplyDto replyDto, HttpSession session) {
		String userId = (String)session.getAttribute("userId");
		replyDto.setReplyWriter(userId);
		
		int replyNo = replyDao.sequence();
		replyDto.setReplyNo(replyNo);
		
		replyDao.insert(replyDto);//댓글 등록
		boardDao.updateBoardReply(replyDto.getReplyOrigin());//댓글개수 갱신
	}
	
	//댓글 삭제
	@PostMapping("/delete")
	public void delete(@RequestParam int replyNo) {
		ReplyDto replyDto = replyDao.selectOne(replyNo);
		replyDao.delete(replyNo);//댓글 삭제
		boardDao.updateBoardReply(replyDto.getReplyOrigin());//댓글개수 갱신
	}
	
	//댓글 수정
	@PostMapping("/edit")
	public void edit(@ModelAttribute ReplyDto replyDto) {
		replyDao.update(replyDto);
	}
}
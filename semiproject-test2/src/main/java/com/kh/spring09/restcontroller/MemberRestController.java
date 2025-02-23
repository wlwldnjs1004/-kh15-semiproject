package com.kh.spring09.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring09.dao.MemberDao;
import com.kh.spring09.dto.MemberDto;

@CrossOrigin
@RestController
@RequestMapping("/rest/member")
public class MemberRestController {

	
	@Autowired
	private MemberDao memberDao;
	
	@RequestMapping("/checkMemberId")
	public boolean checMemberId(@RequestParam String memberId) {
		MemberDto memberDto=memberDao.selectOne(memberId);
		return memberDto ==null;//사용 가능하면 true 반환
	}
	
	
//	@RequestMapping("/checkMemberNickname")
}

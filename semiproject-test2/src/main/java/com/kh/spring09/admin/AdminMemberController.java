package com.kh.spring09.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring09.dao.MemberDao;
import com.kh.spring09.dto.MemberDto;
import com.kh.spring09.dto.PlayerDto;
import com.kh.spring09.error.TargetNotFoundException;
import com.kh.spring09.vo.PageVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

	@Autowired
	private MemberDao memberDao ;
	
	
	@RequestMapping("/list")
	public String list(Model model, @ModelAttribute("pageVO")PageVO pageVO) {
model.addAttribute("list",memberDao.selectList(pageVO));
	int count=memberDao.count(pageVO);
	pageVO.setCount(count);
		return"/WEB-INF/views/admin/member/list.jsp";
	}
	@RequestMapping("/detail")
	public String detail(@RequestParam String memberId, Model model) {
		MemberDto memberDto=memberDao.selectOne(memberId);
		if(memberDto==null) {
			throw new TargetNotFoundException("존제하지 않는 회원");
		}
model.addAttribute("memberDto",memberDto);
return"/WEB-INF/views/admin/member/detail.jsp";
	}
@RequestMapping("/delete")
public String delete(@RequestParam String memberId) {
	boolean success=memberDao.delete(memberId);
	if(success) {
		throw new TargetNotFoundException("존제하지 않는 회원");
	}
return "redirect:home";

}
//@GetMapping()

//@PostMapping
}

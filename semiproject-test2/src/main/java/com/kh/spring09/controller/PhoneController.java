package com.kh.spring09.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring09.dao.PhoneDao;
import com.kh.spring09.dto.LectureDto;
import com.kh.spring09.dto.PhoneDto;
import com.kh.spring09.dto.PokemonDto;
import com.kh.spring09.vo.PageVO;

@Controller
@RequestMapping("/phone")
public class PhoneController {

	@Autowired
	public PhoneDao phoneDao;
	
	@GetMapping("/add")
	public String add() {
		return"/WEB-INF/views/phone/add.jsp";
	}
@PostMapping("/add")
public String add(@ModelAttribute PhoneDto phoneDto) {
	phoneDao.insert(phoneDto);
	return"redirect:addFinish";
	
}
@RequestMapping("/addFinish")
public String addP() {
	return"/WEB-INF/views/phone/addFinish.jsp";
}
//목록 및 검색 매핑
//@RequestMapping("/list")
//public String list(@RequestParam(required = false)String column,
//		@RequestParam(required = false)String keyword,
//		Model model) {
//	boolean search= column !=null && keyword !=null;
//	List<PhoneDto> list;
//	
//	if(search) {
//		model.addAttribute("list",phoneDao.selectList(column, keyword));
//	}
//	else {
//		model.addAttribute("list",phoneDao.selectList());
//	}
//model.addAttribute("search",search);
//model.addAttribute("column",column);
//model.addAttribute("keyword",keyword);
//
//return"/WEB-INF/views/phone/list.jsp";
//}


@RequestMapping("/list")
public String list(Model model,@ModelAttribute("pageVO") PageVO pageVO) {
	//model.addAttribute("pageVO,pageVO);
	model.addAttribute("list",phoneDao.selectListByPaging(pageVO));


	//게시글 수
	
	int count=phoneDao.count(pageVO);
	pageVO.setCount(count);
	
	
	return "/WEB-INF/views/phone/list.jsp";
}


@RequestMapping("/detail")
public String detail(@RequestParam int phoneNo,Model model) {
	PhoneDto phoneDto=phoneDao.selectOne(phoneNo);
model.addAttribute("phoneDto",phoneDto);													
return"/WEB-INF/views/phone/detail.jsp";

}
@RequestMapping("/delete")
public String delete(@RequestParam int phoneNo) {
phoneDao.delete(phoneNo);
return"redirect:list";

}

}
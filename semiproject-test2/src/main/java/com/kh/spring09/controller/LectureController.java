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

import com.kh.spring09.dao.LectureDao;
import com.kh.spring09.dto.LectureDto;

@Controller
@RequestMapping("/lecture")
public class LectureController {

	@Autowired
	public LectureDao lectureDao;
	
	@GetMapping("/add")
	public String add() {
		return"/WEB-INF/views/lecture/add.jsp";
		
	}
@PostMapping("/add")
public String add(@ModelAttribute LectureDto lectureDto) {
	lectureDao.insert(lectureDto);
return"redirect:addFinish";
}
@RequestMapping("/addFinish")
public String addFinish() {
	return"/WEB-INF/views/lecture/addFinish.jsp";
}
//목록 및 검색 매핑
@RequestMapping("/list")
public String list(@RequestParam(required = false)String column,
		@RequestParam(required = false)String keyword,
		Model model) {
	boolean search= column !=null && keyword !=null;
	List<LectureDto> list;
	
	if(search) {
		model.addAttribute("list",lectureDao.selectList(column, keyword));
	}
	else {
		model.addAttribute("list",lectureDao.selectList());
	}
model.addAttribute("search",search);
model.addAttribute("column",column);
model.addAttribute("keyword",keyword);

return"/WEB-INF/views/lecture/list.jsp";
}

@RequestMapping("/delete")
public String delete(@RequestParam int lectureNo) {
	lectureDao.delete(lectureNo);
	return"redirect:list";

}
}



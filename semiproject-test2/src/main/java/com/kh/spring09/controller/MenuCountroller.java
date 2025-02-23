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

import com.kh.spring09.dao.MenuDao;
import com.kh.spring09.dto.LectureDto;
import com.kh.spring09.dto.MenuDto;
import com.kh.spring09.dto.PokemonDto;
import com.kh.spring09.vo.PageVO;

@Controller
@RequestMapping("/menu")
public class MenuCountroller {

	@Autowired
	private MenuDao menuDao;
	
	@GetMapping("/add")
	public String add() {
		return "/WEB-INF/views/menu/add.jsp";
	}
@PostMapping("/add")
public String Madd(@ModelAttribute MenuDto menuDto) {
	menuDao.insert(menuDto);
	return "redirect:addFinish";
	
	
}
@RequestMapping("/addinish")
public String add3() {
	return"/WEB-INF/views/menu/addFinish.jsp";
}
//목록 및 검색 매핑
//@RequestMapping("/list")
//public String list(@RequestParam(required = false)String column,
//		@RequestParam(required = false)String keyword,
//		Model model) {
//	boolean search= column !=null && keyword !=null;
//	List<MenuDto> list;
//	
//	if(search) {
//		model.addAttribute("list",menuDao.selectList(column, keyword));
//	}
//	else {
//		model.addAttribute("list",menuDao.selectList());
//	}
//model.addAttribute("search",search);
//model.addAttribute("column",column);
//model.addAttribute("keyword",keyword);
//
//return"/WEB-INF/views/menu/list.jsp";
//}

@RequestMapping("/list")
public String list(Model model,@ModelAttribute("pageVO") PageVO pageVO) {
	//model.addAttribute("pageVO,pageVO);
	model.addAttribute("list",menuDao.selectListByPaging(pageVO));


	//게시글 수
	
	int count=menuDao.count(pageVO);
	pageVO.setCount(count);
	
	
	return "/WEB-INF/views/menu/list.jsp";
}



@RequestMapping("/detail")
public String detail(@RequestParam int menuNo,Model model) {
	MenuDto menuDto=menuDao.selectOne(menuNo);
model.addAttribute("menuDto",menuDto);													
return"/WEB-INF/views/menu/detail.jsp";

}


@RequestMapping("/delete")
public String delete(@RequestParam int menuNo) {
	menuDao.delete(menuNo);
	return"redirect:list";
}


}


package com.kh.spring09.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring09.dto.LectureDto;
import com.kh.spring09.dto.PokemonDto;

import lombok.experimental.PackagePrivate;
//
//@Controller
//@RequestMapping("/item")
//public class ItemController {
//
//	@Autowired
//public ItemDao itemDao;
//
//@GetMapping("/add")
//	public String add() {
//	return"/WEB-INF/views/item/add.jsp";
//}
//@PostMapping("/add")
//public String add(@ModelAttribute ItemDto itemDto){
//itemDao.insert(itemDto);	
//return"redirect:addFinish";
//
//
//}
//@RequestMapping("/addFinish")
//public String IaddFinish() {
//	return"/WEB-INF/views/item/addFinish.jsp";
//}
////목록 및 검색 매핑
//@RequestMapping("/list")
//public String list(@RequestParam(required = false)String column,
//		@RequestParam(required = false)String keyword,
//		Model model) {
//	boolean search= column !=null && keyword !=null;
//	List<ItemDto> list;
//	
//	if(search) {
//		model.addAttribute("list",itemDao.selectList(column, keyword));
//	}
//	else {
//		model.addAttribute("list",itemDao.selectList());
//	}
//model.addAttribute("search",search);
//model.addAttribute("column",column);
//model.addAttribute("keyword",keyword);
//
//return"/WEB-INF/views/item/list.jsp";
//}
//
//@RequestMapping("/delete")
//public String deleString(@RequestParam int itemNo) {
//	itemDao.delete(itemNo);
//	return"redirec:list";
//}
//
//@GetMapping("/edit")
//public String edit(@RequestParam int itemNo,Model model) {		
//	ItemDto itemDto=itemDao.selectOne(itemNo);
//	model.addAttribute("itemDto",itemDto);
//	//화면 지정
//	return"/WEB-INF/views/item/edit.jsp";	
//}
//@PostMapping("/edit")
//public String edit(@ModelAttribute ItemDto itemDto) {
//	boolean success=itemDao.update(itemDto);
//if(success) {	//참고: redirect는 다른 매핑으로 GET방식 요청을 생성하는 것이므로 생성가능 [주소 지정]주소에선 ?을 지정 자체가 못함
//	return"redirect:detail?itemNo="+itemDto.getItemNo();
//}
//else {
//	return"redirect:list";
//}
//	
//}
//}
//

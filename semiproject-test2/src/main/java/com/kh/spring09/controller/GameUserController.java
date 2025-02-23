package com.kh.spring09.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring09.Service.AttachmentService;
import com.kh.spring09.dao.GameUserDao;
import com.kh.spring09.dto.GameUserDto;
import com.kh.spring09.dto.PokemonDto;
import com.kh.spring09.vo.PageVO;

@Controller
@RequestMapping("/game-user")
public class GameUserController {

	@Autowired
	public GameUserDao gameUserDao;

	@Autowired
	private AttachmentService attachmentService;
	
@GetMapping("/add")
public String add1() {
	return"/WEB-INF/views/game-user/add.jsp";
	
	
}
//@PostMapping("/add")
//public String add2(@ModelAttribute GameUserDto gameUserDto) {
//	if(gameUserDto.getGameUserLevel()==0) {
//		gameUserDto.setGameUserLevel(1);
//	}
//	gameUserDao.insert(gameUserDto);
//	return"redirect:add-finish";
//}

@PostMapping("/add")
public String add(@ModelAttribute GameUserDto gameUserDto,
		@RequestParam MultipartFile attach) throws IllegalStateException, IOException {

	if(gameUserDto.getGameUserLevel()==0) {
	gameUserDto.setGameUserLevel(1);
}
	int gameUserNo=gameUserDao.sequence();
	gameUserDto.setGameUserNo(gameUserNo);
	gameUserDao.inesrt2(gameUserDto);
	if(attach.isEmpty()==false) {
		int attachmentNo= attachmentService.save(attach);
		gameUserDao.connect(gameUserNo, attachmentNo);
		
	}
	return"redirect:add-finish";
}

@RequestMapping("/add-finish")
public String add3() {
	return"/WEB-INF/views/game-user/add-finish.jsp";
}
	



//목록 및 검색 매핑
//@RequestMapping("/list")
//public String list(@RequestParam(required = false)String column,
//		@RequestParam(required = false)String keyword,
//		Model model) {
//	boolean search= column !=null && keyword !=null;
//	List<GameUserDto> list;
//	
//	if(search) {
//		model.addAttribute("list",gameUserDao.selectList(column, keyword));
//	}
//	else {
//		model.addAttribute("list",gameUserDao.selectList());
//	}
//model.addAttribute("search",search);
//model.addAttribute("column",column);
//model.addAttribute("keyword",keyword);
//
//return"/WEB-INF/views/game-user/list.jsp";
//}
@RequestMapping("/list")
public String list(Model model,@ModelAttribute("pageVO") PageVO pageVO) {
	//model.addAttribute("pageVO,pageVO);
	model.addAttribute("list",gameUserDao.selectListByPaging(pageVO));


	//게시글 수
	
	int count=gameUserDao.count(pageVO);
	pageVO.setCount(count);
	
	
	return "/WEB-INF/views/game-user/list.jsp";
}






@RequestMapping("/detail")
public String detail(@RequestParam int gameUserNo,Model model) {
	GameUserDto gameUserDto=gameUserDao.selectOne(gameUserNo);
model.addAttribute("gameUserDto",gameUserDto);													
return"/WEB-INF/views/game-user/detail.jsp";

}
@RequestMapping("/delete")
public String delete(@RequestParam int gameUserNo) {
	try {
		int attachmentNo= gameUserDao.findAttachment(gameUserNo);
		attachmentService.delete(attachmentNo);
	}
	catch(Exception e) {}
	
	gameUserDao.delete(gameUserNo);
return"redirect:list";
//return"redirect:/game-user/list;절대경로

}
@GetMapping("/edit")
public String edit(@RequestParam int gameUserNo,Model model) {		
GameUserDto gameUserDto=gameUserDao.selectOne(gameUserNo);
model.addAttribute(gameUserDto);
return"/WEB-INF/views/game-user/edit.jsp";
}


@PostMapping("/edit")
public String edit(@ModelAttribute GameUserDto gameUserDto,
		@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
	boolean success=gameUserDao.update(gameUserDto);
if(!success) {
	return"redirect:list";

}
if(attach.isEmpty()==false) {
	try {
		int attachmentNo=gameUserDao.findAttachment(gameUserDto.getGameUserNo());
		attachmentService.delete(attachmentNo);
	}catch(Exception e) {/*아무것도 안함*/}
	int newAttachmentNo = attachmentService.save(attach);
	gameUserDao.connect(gameUserDto.getGameUserNo(), newAttachmentNo);

}
return"redirect:detail?gameUserNo="+gameUserDto.getGameUserNo();
}



@RequestMapping("/levelup")
public String levelup(@RequestParam int gameUserNo) {
	GameUserDto gameUserDto =gameUserDao.selectOne(gameUserNo);
	int level=gameUserDto.getGameUserLevel();
			gameUserDto.setGameUserLevel(level+1);
			gameUserDao.update(gameUserDto);
			//game_userDao.inctraseGameUserLevel(gameUserNo);
			//
			return"redirect:detail?gameUserNo="+gameUserNo;
}
@RequestMapping("/profile")
public String image(@RequestParam int gameUserNo) {
	try {
		int attachmentNo=gameUserDao.findAttachment(gameUserNo);
		return "redirect:/attachment/download?attachmentNo="+attachmentNo;
	}
	catch (Exception e) {}
//	return"redirect:/images/images.png";
	return"redirect:https://placehold.co/400x400?text=P";
}

}



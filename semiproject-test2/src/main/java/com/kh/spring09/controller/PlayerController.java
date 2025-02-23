package com.kh.spring09.controller;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring09.Service.AttachmentService;
import com.kh.spring09.dao.PlayerDao;
import com.kh.spring09.dto.PlayerDto;

@Controller
@RequestMapping("/player")
public class PlayerController {

	
	@Autowired
	private PlayerDao playerDao;
	
	@Autowired
	private  AttachmentService attachmentService;
	
	@GetMapping("/add")
	public String add() {
		return"/WEB-INF/views/player/add.jsp";

}
@PostMapping("/add")
public String add(@ModelAttribute PlayerDto playerDto,
		@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
int playerNo=playerDao.sequence();
playerDto.setPlayerNo(playerNo);
playerDao.inesrt2(playerDto);
	if(attach.isEmpty()==false) {
		int attachmentNo=attachmentService.save(attach);
		playerDao.connect(playerNo, attachmentNo);
		
	}
	return"redirect:addFinish";
}
@RequestMapping("/addFinish")	
public String add3() {
	return"/WEB-INF/views/player/addFinish.jsp";
}

@RequestMapping("/detail")
public String detail(@RequestParam int playerNo,Model model) {
	PlayerDto playerDto=playerDao.selectOne(playerNo);
model.addAttribute("playerDto",playerDto);													
return"/WEB-INF/views/player/detail.jsp";

}
@RequestMapping("/list")
public String list(@RequestParam(required = false)String column,
		@RequestParam(required = false)String keyword,
		Model model) {
	boolean search= column !=null && keyword !=null;
	List<PlayerDto> list;
	
	if(search) {
		model.addAttribute("list",playerDao.selectList(column, keyword));
	}
	else {
		model.addAttribute("list",playerDao.selectList());
	}
model.addAttribute("search",search);
model.addAttribute("column",column);
model.addAttribute("keyword",keyword);

return"/WEB-INF/views/player/list.jsp";
}


@RequestMapping("/delete")
public String delete(@RequestParam int playerNo) {
try {
int attachmentNo=playerDao.findAttachment(playerNo);
attachmentService.delete(attachmentNo);
}
	catch (Exception e) {}

	playerDao.delete(playerNo);
return"redirect:list";


}

//수성
@GetMapping("/edit")
public String edit(@RequestParam int playerNo,Model model) {		
	PlayerDto playerDto=playerDao.selectOne(playerNo);
	model.addAttribute("playerDto",playerDto);
	//화면 지정
	return"/WEB-INF/views/player/edit.jsp";	
}
@PostMapping("/edit")
public String edit(@ModelAttribute PlayerDto playerDto,
		@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
	boolean success=playerDao.update(playerDto);
	if(!success) {
		return"redirect:list";
	}
	if(attach.isEmpty()==false) {
		try {
			int attachmentNo=playerDao.findAttachment(playerDto.getPlayerNo());
			attachmentService.delete(attachmentNo);
			
		}catch (Exception e) {}
		int newAttachmentNo= attachmentService.save(attach);
		playerDao.connect(playerDto.getPlayerNo(), newAttachmentNo);
	}
	return"redirect:list";
}
	@RequestMapping("/image")
	public String image(@RequestParam int playerNo) {
	try {
		int attachmentNo=playerDao.findAttachment(playerNo);
		return "redirect:/attachment/download?attachmentNo="+attachmentNo;
	}
	catch (Exception e) {}
	return"redirect:/images/images.png";
//	return"redirect:https://placehold.co/400x400?text=P";
	}
}

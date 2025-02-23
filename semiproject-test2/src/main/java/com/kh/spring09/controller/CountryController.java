package com.kh.spring09.controller;

import java.io.IOException;
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
import com.kh.spring09.dao.CountryDao;
import com.kh.spring09.dto.CountryDto;
import com.kh.spring09.vo.PageVO;

@Controller
@RequestMapping("/country")
public class CountryController {

	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@GetMapping("/add")//GET
	public String add() {
		
		return"/WEB-INF/views/country/add.jsp";
	}
	@PostMapping("/add")
	public String add(@ModelAttribute CountryDto countryDto, 
			@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
		
		int countryNo=countryDao.sequence();
		countryDto.setCountryNo(countryNo);
		countryDao.inesrt2(countryDto);
	
		if(attach.isEmpty()==false) {
			int attachmentNo=attachmentService.save(attach);
			countryDao.connect(countryNo, attachmentNo);
			
		}
		
		return"redirect:addFinish";
	}
	
	
	
	@RequestMapping("/addFinish")//방식무관
	public String add3() {
		return"/WEB-INF/views/country/addFinish.jsp";
	}
	
	
	
	
	
	
	@RequestMapping("/list")
	public String list(Model model,@ModelAttribute("pageVO") PageVO pageVO) {
		//model.addAttribute("pageVO,pageVO);
		model.addAttribute("list",countryDao.selectListByPaging(pageVO));


		//게시글 수
		
		int count=countryDao.count(pageVO);
		pageVO.setCount(count);
		
		
		return "/WEB-INF/views/country/list.jsp";
	}


	
	
	
	@RequestMapping("/detail")
	public String detail(@RequestParam int countryNo,Model model) {
		CountryDto countryDto=countryDao.selectOne(countryNo);
	model.addAttribute("countryDto",countryDto);													
	return"/WEB-INF/views/country/detail.jsp";

	}
	@RequestMapping("/delete")
	public String delete(@RequestParam int countryNo) {
		try {
			int attachmentNo = countryDao.findAttachment(countryNo);
			attachmentService.delete(attachmentNo);
		}
		catch(Exception e) {}
		
		countryDao.delete(countryNo);
	return"redirect:list";

	}
	@GetMapping("/edit")
	public String edit(@RequestParam int countryNo,Model model) {		
		CountryDto countryDto=countryDao.selectOne(countryNo);
	model.addAttribute(countryDto);
	return"/WEB-INF/views/country/edit.jsp";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute CountryDto countryDto,
			@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
		
		boolean success=countryDao.update(countryDto);
		if(!success) {
			return"redirect:list";
		}
		if(attach.isEmpty()==false) {
		try {
			 int attachmentNo = countryDao.findAttachment(countryDto.getCountryNo());
			attachmentService.delete(attachmentNo);
		
		}catch(Exception e) {}
		int newAttachmentNo= attachmentService.save(attach);
		countryDao.connect(countryDto.getCountryNo(), newAttachmentNo);
	}
return"redirect:detail?countryNo="+countryDto.getCountryNo();
}
@RequestMapping("/image")
public String image(@RequestParam int countryNo) {
	try {
		int attachmentNo= countryDao.findAttachment(countryNo);
		return"redirect:/attachment/download?attachmentNo="+attachmentNo;
	}
	catch (Exception e) {}
return "redirect:/images/images.png";
//return "redirect:https://placehold.co/400x400?text=P";
}

}
		



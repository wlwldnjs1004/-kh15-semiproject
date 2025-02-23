package com.kh.spring09.admin;

import java.io.IOException;

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
import com.kh.spring09.dao.AttachmentDao;
import com.kh.spring09.dao.GiftcardDao;
import com.kh.spring09.dto.GiftcardDto;

@Controller
@RequestMapping("/admin/giftcard")
public class AdminGiftcardController {
	
	@Autowired
	private GiftcardDao giftcardDao;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private AttachmentDao attachmentDao;
	
	//상품권 등록
	@GetMapping("/add")
	public String add() {
		return "/WEB-INF/views/admin/giftcard/add.jsp";
	}
	@PostMapping("/add")
	public String add(@ModelAttribute GiftcardDto giftcardDto,
								@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
		//첨부파일이 없는경우를 제거
		if(attach.isEmpty()) {
			return "redirect:add?error";
		}
		
		int giftcardNo = giftcardDao.sequence();
		giftcardDto.setGiftcardNo(giftcardNo);
		giftcardDao.insert(giftcardDto);
		
		int attachmentNo = attachmentService.save(attach);
		
		giftcardDao.connect(giftcardNo, attachmentNo);
		
		return "redirect:list";//목록으로 이동
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", giftcardDao.selectList());
		return "/WEB-INF/views/admin/giftcard/list.jsp";
	}
	
	//이미지 매핑
	@RequestMapping("/image")
	public String image(@RequestParam int giftcardNo) {
		try {
			int attachmentNo = giftcardDao.findAttachment(giftcardNo);
			return "redirect:/attachment/download?attachmentNo="+attachmentNo;
		}
		catch(Exception e) {
			return "redirect:https://placehold.co/200x80?text=GIFT";
		}
		//실험 페이지 메핑
		
	}

		
	
}

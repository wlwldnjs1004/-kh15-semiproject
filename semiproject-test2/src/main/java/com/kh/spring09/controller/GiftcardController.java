package com.kh.spring09.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring09.dao.GiftcardDao;
import com.kh.spring09.dao.GiftcardPurchaseDao;
import com.kh.spring09.dao.MemberDao;
import com.kh.spring09.dto.GiftcardDto;
import com.kh.spring09.dto.GiftcardPurchaseDto;
import com.kh.spring09.error.TargetNotFoundException;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/giftcard")
public class GiftcardController {

	@Autowired
	private GiftcardDao giftcardDao;

	
	@Autowired
	private GiftcardPurchaseDao giftcardPurchaseDao;
	
	@Autowired
	private MemberDao memberDao;
	
	//목록 매핑
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", giftcardDao.selectList());
		return "/WEB-INF/views/giftcard/list.jsp";
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
	}
	
	//구매 매핑
	@GetMapping
	("/purchase")
	public String purchase(@RequestParam int giftcardNo, Model model) {
		GiftcardDto giftcardDto = giftcardDao.selectOne(giftcardNo);
		if(giftcardDto == null)
			throw new TargetNotFoundException("존재하지 않는 상품권");
		
		model.addAttribute("giftcardDto", giftcardDto);
		return "/WEB-INF/views/giftcard/purchase.jsp";
	}
	@PostMapping("/purchase")
	public String purchase(
			@ModelAttribute GiftcardPurchaseDto giftcardPurchaseDto, 
			HttpSession session) {
		//giftcard_purchase 테이블에 정보를 등록
		String userId = (String)session.getAttribute("userId");
		giftcardPurchaseDto.setGiftcardPurchaseMember(userId);
		
		//상품이 판매 가능한지 등의 검사가 필요할 경우 이곳에 추가
		
		GiftcardDto giftcardDto= giftcardDao.selectOne(giftcardPurchaseDto.getGiftcardPurchaseTarget());
		if(giftcardDto==null) {
			throw new TargetNotFoundException("존제하지 않는 상품 구매");
		}
		//실 포인트 충전 작업
	memberDao.plusMemberPoint(userId,giftcardDto.getGiftcardCharge());
		
		//구매 내역 추가
		int giftcardPurchaseNo = giftcardPurchaseDao.sequence();
		giftcardPurchaseDto.setGiftcardPurchaseNo(giftcardPurchaseNo);
		
		giftcardPurchaseDao.insert(giftcardPurchaseDto);
		
		return "redirect:purchaseFinish";
	}
	@RequestMapping("/purchaseFinish")
	public String purchaseFinish() {
		return "/WEB-INF/views/giftcard/purchaseFinish.jsp";
	}
	
	
}

	

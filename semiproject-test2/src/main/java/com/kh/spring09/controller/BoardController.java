package com.kh.spring09.controller;


import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring09.Service.AttachmentService;
import com.kh.spring09.dao.BoardDao;
import com.kh.spring09.dao.BoardListViewDao;
import com.kh.spring09.dao.MemberDao;
import com.kh.spring09.dto.BoardDto;
import com.kh.spring09.dto.MemberDto;
import com.kh.spring09.error.TargetNotFoundException;
import com.kh.spring09.vo.PageVO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")//아이 오 씨
public class BoardController {
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private BoardListViewDao boardListViewDao;
	
	@Autowired
	private AttachmentService attachmentService;
	
	//목록 및 매핑
	//- 검색을 위해 column, keyword 항목을 수신
	//- 페이징을 위해 page, size 항목을 수신
	
	//Dto 테이블과 같다
	//PageVO/ 맘대로 만든다
	
//	@RequestMapping("/list")
//	public String list(Model model, 
//			@RequestParam(required=false) String column, 
//			@RequestParam(required=false) String keyword, 
//			@RequestParam(required=false, defaultValue = "1") int page, 
//			@RequestParam(required=false, defaultValue = "10") int size) {
//		boolean search = column != null && keyword != null;
//		if(search) {//검색
//			model.addAttribute("list", 
//				boardDao.selectListByPaging(column, keyword, page, size));
//		}
//		else {//목록
//			model.addAttribute("list", 
//				boardDao.selectListByPaging(page, size));
//		}
//		
//		//페이징에 필요한 데이터들을 전달
//		model.addAttribute("search", search);//검색 여부
//		model.addAttribute("page", page);//현재 페이지 번호
//		model.addAttribute("size", size);//현재 페이지 크기
//		model.addAttribute("column", column);//검색항목
//		model.addAttribute("keyword", keyword);//검색어
//		int startBlock = (page-1) / 10 * 10 + 1;
//		int finishBlock = (page-1) / 10 * 10 + 10;
//		model.addAttribute("startBlock", startBlock);//블록의 시작번호
//		//model.addAttribute("finishBlock", finishBlock);//블록의 종료번호
//		
//		//게시글 수
//		int count;
//		if(search) {
//			count = boardDao.count(column, keyword);
//		}
//		else {
//			count = boardDao.count();
//		}
//		//페이지 수
//		int pageCount = (count-1) / size + 1;
//		model.addAttribute("count", count);
//		model.addAttribute("pageCount", pageCount);
//		
//		model.addAttribute("finishBlock", Math.min(pageCount, finishBlock));//블록의 종료번호
//		
//		return "/WEB-INF/views/board/list.jsp";
//	}
//	vo를 이용하여 구조를
	//ModelAttribute는  자동으로 Model 에 추가되는 숨겨진 기능이 있다
//ModelAttribute는  자동으로 Model 에 추가되는 숨겨진 기능이 있다 @pageVo란 이름으로 추가됨
	
	@RequestMapping("/list")
	public String list(Model model,@ModelAttribute("pageVO") PageVO pageVO) {
		//model.addAttribute("pageVO,pageVO);
//		model.addAttribute("list",boardDao.selectListByPaging(pageVO));
			model.addAttribute("list",boardListViewDao.selectList(pageVO));
		//게시글 수
		
		int count=boardDao.count(pageVO);
		pageVO.setCount(count);
		
		
		return "/WEB-INF/views/board/list.jsp";
	}

	
	//게시글 상세 매핑
	@RequestMapping("/detail")
	public String detail(@RequestParam int boardNo, Model model) {
		BoardDto boardDto = boardDao.selectOne(boardNo);
		
		model.addAttribute("boardDto", boardDto);
	
		//만약에 작성자가 존재한다면 해당 작성자의 정보를 추가로 조히해서 전달

		if(boardDto.getBoardWriter() !=null) {
			
		MemberDto memberDto=memberDao.selectOne(boardDto.getBoardWriter());
		model.addAttribute("memberDto",memberDto);
		}		
		
		
		return "/WEB-INF/views/board/detail.jsp";
	}
	
	//게시글 작성 매핑
	@GetMapping("/write")
	public String write() {
		return "/WEB-INF/views/board/write.jsp";
	}
//	@PostMapping("/write")
//	public String write(@ModelAttribute BoardDto boardDto, 
//															HttpSession session) {
//		//사용자는 2개의 정보(제목, 내용)만 작성
//		//작성자 정보는 HttpSession에 있으므로 이를 꺼내어 합쳐서 등록
//		String userId = (String)session.getAttribute("userId");
//		boardDto.setBoardWriter(userId);
//		boardDao.insert(boardDto);
//		return "redirect:list";//목록으로 리다이렉트
//	}
	@PostMapping("/write")
	public String write(@ModelAttribute BoardDto boardDto, 
															HttpSession session) {
		//사용자는 2개의 정보(제목, 내용)만 작성
		//작성자 정보는 HttpSession에 있으므로 이를 꺼내어 합쳐서 등록
		String userId = (String)session.getAttribute("userId");
		boardDto.setBoardWriter(userId);
		
		
		int boardNo = boardDao.sequence();//시퀀스 번호 발행
		boardDto.setBoardNo(boardNo);//번호 설정
		
		
		//새글과 답글을 구부낳여 정보를 설정한 뒤 등록하도록 처리
		//-새글일 경우 그룹번호=글번호, 상위글=null, 차수=0으로 설정
		//답글일 경우 그룹번호=대상글과 동일 , 상위글=대상글번호, 차수=대상극차수+1로 설정
		//- boardTarget이 없으면 새글, 있으면 답글
		if(boardDto.getBoardTarget()==null) {//새글이라면
			boardDto.setBoardGroup(boardNo);
			boardDto.setBoardTarget(null);//상위글 번호는 null로 설정
			boardDto.setBoardDepth(0);//차수는 0으로 설정
			
		}
		else {// 답글이라면
			BoardDto targetDto= boardDao.selectOne
					(boardDto.getBoardTarget());// 원본글 조회
			
			boardDto.setBoardGroup(targetDto.getBoardGroup());//그룹 번호는
			boardDto.setBoardTarget(targetDto.getBoardNo());//상위글 번호는
			boardDto.setBoardDepth(targetDto.getBoardDepth()+1);//차수는 0으로 설정
			
		}
		boardDao.insert2(boardDto);//등록
		
		
		return "redirect:detail?boardNo="+boardNo;//상세로 리다이렉트
	}
	
	//게시글 삭제 매핑
	//(+추가)rmfdksdp emfdjdltsms dlalwl(class=summernote-img)를 찾아서 모두
	
	@RequestMapping("/delete")
	public String delete(@RequestParam int boardNo) {
	BoardDto boardDto=boardDao.selectOne(boardNo);
	
	if(boardDto==null) {
		throw new TargetNotFoundException("존제하지 않는글");
		
	}
	
	String boardContent=boardDto.getBoardContent();
	
	
	//본문에 포함된 class"summenrote-ㅑㅡㅎfmf ckwdktj tkrwp
	//jQuery 였다면 $(".summernot-img").data("attachment-no");
	//-Jsoup 라이버리리를 이용하여 문서를 HTML로 해석
	
	Document document=Jsoup.parse(boardContent);//문서 해석
	Elements elements=document.select(".summernote-img");//태그 탐색
	for(Element element:elements) {//탐색결과 반복
		String dataset=element.attr("data-attachment-no");//속성 추출
		int attachmentNo=Integer.parseInt(dataset);//정수로 변환
		attachmentService.delete(attachmentNo);//삭제 요청
		
	}
		boardDao.delete(boardNo);
		return "redirect:list";
	
	
	}
	
	//게시글 수정 매핑
	@GetMapping("/edit")
	public String edit(@RequestParam int boardNo, Model model) {
		BoardDto boardDto = boardDao.selectOne(boardNo);
		model.addAttribute("boardDto", boardDto);
		return "/WEB-INF/views/board/edit.jsp";
	}
	@PostMapping("/edit")
	public String edit(@ModelAttribute BoardDto boardDto) {
		//(+추가) 이미지의 변화를 감지해서 삭제할 대상을 찾아 제거
		//-기존 이미지 목록과 신규 이미지 목록을 비교해서 대상을 찾는다
		//-기존 이미지가 1,2,3,4번이고 신규 이미지가 1,2,5,6번이면?
		//-제거할 이미지는 3,4번
		//-집합연산(차집합)으로 찾는다
		
		//원래 글을 찾는다
		BoardDto originDto=boardDao.selectOne(boardDto.getBoardNo());
		if(originDto !=null)throw new TargetNotFoundException("존재하지 않는");
		
		//수정 전 이미지 집합 생성(
		Set<Integer> before=new HashSet<>();
		Document beforeDocument=Jsoup.parse(originDto.getBoardContent());
		for(Element element:beforeDocument.select(".summernote-img")) {
		int attachmentNo=Integer.parseInt(element.attr("data-attachment-no"));
		before.add(attachmentNo);
		
		
		}
		
		//수정 후 이미지 집합 생성
		Set<Integer> after=new HashSet<>();
		Document afterDocument=Jsoup.parse(boardDto.getBoardContent());
		for(Element element: afterDocument.select(".summernote-img")) {
			int attachmentNo=Integer.parseInt(element.attr("data-attachment-no"));
		after.add(attachmentNo);
		
		}
		//수정 전 -수정후 차집합 계산
		Set<Integer> minus=new HashSet<>(before);
		minus.remove(after);
		
		//구해진 차집합의 내용만큼 이미지를 제거
for(int attachmentNo:minus) {
	attachmentService.delete(attachmentNo);
}
		
		
		
		boardDao.update(boardDto);
		return "redirect:detail?boardNo="+boardDto.getBoardNo();
	}
}



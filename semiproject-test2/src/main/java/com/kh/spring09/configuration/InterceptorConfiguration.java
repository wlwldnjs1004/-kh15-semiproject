package com.kh.spring09.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.spring09.aop.AdminLoginInterceptor;
import com.kh.spring09.aop.BoardOwnerlnterceptor;
import com.kh.spring09.aop.BoardReadInterceptor;
import com.kh.spring09.aop.BoardReadInterceptor2;
import com.kh.spring09.aop.BoardReadInterceptor3;
import com.kh.spring09.aop.MemberLoginlnterceptor;
import com.kh.spring09.aop.TestLoglnterceptor;

//설정파일(Configuration)
//- application.properties에서 할 수 없는 복잡한 설정을 구현할 때 사용
//- @Configuration으로 등록
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

	@Autowired
	private TestLoglnterceptor testLoglnterceptor;
	@Autowired
	private MemberLoginlnterceptor memberLoginInterceptor;
	@Autowired
	private BoardOwnerlnterceptor boardOwnerInterceptor;
	@Autowired
	private BoardReadInterceptor boardReadInterceptor;
	@Autowired
	private BoardReadInterceptor2 boardReadInterceptor2;
	@Autowired
	private BoardReadInterceptor3 boardReadInterceptor3;
	@Autowired
	private AdminLoginInterceptor adminLoginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//필요한 주소에 인터셉터를 등록
		//registry.addInterceptor(testLogInterceptor).addPathPatterns("/**");
		
		//회원 전용 기능에 대해 인터셉터를 등록
		//- 와일드카드를 한개(*) 쓰면 엔드포인트를 유지하며 패턴 검색
		//- 와일드카드를 두개(**) 쓰면 엔드포인트 이하의 모든 주소 패턴 검색
		registry.addInterceptor(memberLoginInterceptor)
		.addPathPatterns(List.of(
				"/member/**",
				"/game-user/**",
				"/board/**",
				"/giftcard/**"
			))
					.excludePathPatterns(List.of(
						//"/member/join",
						//"/member/joinFinish",
						"/member/join*",//join어쩌구
						"/member/login",
						"/member/exitFinish",
						"/board/list",
						"/board/detail"
					));
		
		//게시글 소유자 검사 인터셉터 추가
		registry.addInterceptor(boardOwnerInterceptor)
						.addPathPatterns(List.of(
							"/board/edit",
							"/board/delete"
						));
		
		//조회 수 증가처리 인터셉터 등록
		registry.addInterceptor(boardReadInterceptor3)
						.addPathPatterns("/board/detail");
		
		//관리자 검사 인터셉터 등록
		registry.addInterceptor(adminLoginInterceptor)
						.addPathPatterns("/admin/**");
	}
}







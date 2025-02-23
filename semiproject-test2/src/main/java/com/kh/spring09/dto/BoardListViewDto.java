package com.kh.spring09.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

//oracle의 board_list_view와 연결될 DTO
@Data
public class BoardListViewDto {
	private int boardNo;
	private String boardTitle;
	//private String boardContent;
	private String boardWriter;
	private Timestamp boardWtime, boardEtime;
	private int boardLike, boardRead, boardReply;
	private int boardGroup;
	private Integer boardTarget;
	private int boardDepth;
	
	//상위글정보
	private int targetNo;
	private String targetTitle;
	
	private String memberId;//아이디
	private String memberPw;//비밀번호
	private String memberNickname;//닉네임
	private String memberBirth;//생년월일
	private String memberContact;//연락처
	private String memberEmail;//이메일
	private String memberLevel;//회원등급
	private int memberPoint;//보유포인트
	private String memberPost;//우편번호
	private String memberAddress1;//기본주소
	private String memberAddress2;//상세주소
	private Timestamp memberJoin;//가입일시
	private Timestamp memberLogin;//최종로그인일시
	private Timestamp memberChange;//최종비밀번호변경일시
	
	//날짜를 출력하기 위한 가상의 메소드
	//- boardWtime과 현재날짜를 비교
	public String getBoardWtimeString() {
		LocalDate today = LocalDate.now();
		LocalDateTime wtime = boardWtime.toLocalDateTime();
		//wtime의 날짜와 today를 비교하여 어떤 값을 내보낼지 결정
		LocalDate wdate = wtime.toLocalDate();
		
//		if(today.isAfter(wdate)) {
		if(wdate.isBefore(today)) {
			return wdate.toString();
		}
		else {
			return wtime.toLocalTime()
						.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
	}
}
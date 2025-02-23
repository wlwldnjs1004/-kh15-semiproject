package com.kh.spring09.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
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
	private Timestamp memberChange;//최종 비밀번호 변경일시

}

package com.kh.spring09.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientDto {

	
	private String clientId;//아이디
	private String clientPassword;//비밀번호
	private String clientNickname;//닉네임
	private String clientGrade;//등급
	private Timestamp clientJoin;//가입일
	private int clientPoint;//포인트
	
}

package com.kh.spring09.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BoardDto {
 private int boardNo;//게시글 번호
 private String	boardTitle;//게시글 제목
 private String boardContent;//게시글 내용
 private String boardWriter;//작성자 ID(null 가능
 private Timestamp boardWtime;//작성 시간
 private Timestamp boardEtime;//수정 시간
 private int boardLike;//좋아요 수
 private int boardRead;//조회 수
 private int boardReply;//댓글 수

 //답변형 게시판을 위해 추가한 데이터
 private int boardGroup;
 private Integer boardTarget;
 private int boardDepth;
 
 
 //   private String memberNickname;//작성자 닉네임

 //-boardWtime과 현재 날짜를 비교
 
 public String getBoardWtimeString() {
	 LocalDate today=LocalDate.now();
   LocalDateTime wtime =boardWtime.toLocalDateTime();
//wtim의 
   LocalDate wdate=wtime.toLocalDate();
   if(wdate.isAfter(today)) {
//   if(today.isAfter(wdate)) {
	  return wdate.toString(); 
   }
   else {
	   return wtime.toLocalTime().
			   format(DateTimeFormatter.ofPattern("HH:mm"));
   }
	
	
}


}

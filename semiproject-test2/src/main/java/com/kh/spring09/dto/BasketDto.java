package com.kh.spring09.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
	public class BasketDto {

		private int basketNo;//고유번호
		private String basketUser;//아이디
		private int basketItem;//상품번호
		private Timestamp basketTime;//추가일시
		private int basketQty;//수량
		
	}


package com.kh.spring09.dto;


import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemDto {
	private int itemNo;//상품번호
	private String itemName;//상품명
	private int itemPrice;//판매가격
	private Timestamp itemRegist;//등록일짜
}

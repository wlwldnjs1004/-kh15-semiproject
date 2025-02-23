package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GiftcardDto {
	private int giftcardNo;//상품권번호
	private String giftcardName;//상품권제목
	private String giftcardContent;//상품권설명
	private int giftcardCharge;//충전포인트
	private int giftcardPrice;//판매가
	
	public int getDiscountRate() {
		return (giftcardCharge - giftcardPrice) * 100 / giftcardCharge;
	}
}
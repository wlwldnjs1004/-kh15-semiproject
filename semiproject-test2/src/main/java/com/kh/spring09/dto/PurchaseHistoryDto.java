package com.kh.spring09.dto;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PurchaseHistoryDto {
	private int giftcardPurchaseNo;//구매번호
	private int giftcardPurchaseTarget;//상품권번호
	private String giftcardPurchaseMember;//구매자ID
	private int giftcardPurchaseQty;//구매수량
	private Timestamp giftcardPurchaseTime;//구매시각
	
	private String giftcardName;//상품권이름
	private int giftcardCharge;//상품권충전포인트
	private int giftcardPrice;//상품권판매금액
	
	public int getGiftcardPurchaseTotal() {
		return giftcardPrice * giftcardPurchaseQty;
	}
}

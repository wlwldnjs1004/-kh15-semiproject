package com.kh.spring09.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.kh.spring09.dto.GiftcardPurchaseDto;
import com.kh.spring09.mapper.GiftcardPurchaseMapper;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.GiftcardPurchaseDto;
import com.kh.spring09.mapper.GiftcardPurchaseMapper;

@Repository
public class GiftcardPurchaseDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private GiftcardPurchaseMapper giftcardPurchaseMapper;
	
	//시퀀스 생성 및 등록
	public int sequence() {
		String sql = "select giftcard_purchase_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	public void insert(GiftcardPurchaseDto giftcardPurchaseDto) {
		String sql = "insert into giftcard_purchase("
							+ "giftcard_purchase_no, giftcard_purchase_target,"
							+ "giftcard_purchase_member, giftcard_purchase_qty"
						+ ") "
						+ "values(?, ?, ?, ?)";
		Object[] data = {
			giftcardPurchaseDto.getGiftcardPurchaseNo(),
			giftcardPurchaseDto.getGiftcardPurchaseTarget(),
			giftcardPurchaseDto.getGiftcardPurchaseMember(),
			giftcardPurchaseDto.getGiftcardPurchaseQty()
		};
		jdbcTemplate.update(sql,data);
	}
	
}


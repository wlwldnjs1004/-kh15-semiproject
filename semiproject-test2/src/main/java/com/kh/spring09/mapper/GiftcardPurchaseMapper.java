package com.kh.spring09.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.GiftcardPurchaseDto;

@Component
public class GiftcardPurchaseMapper implements RowMapper<GiftcardPurchaseDto>{
	@Override
	public GiftcardPurchaseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return GiftcardPurchaseDto.builder()
					.giftcardPurchaseNo(rs.getInt("giftcard_purchase_no"))
					.giftcardPurchaseTarget(rs.getInt("giftcard_purchase_target"))
					.giftcardPurchaseMember(rs.getString("giftcard_purchase_member"))
					.giftcardPurchaseQty(rs.getInt("giftcard_purchase_qty"))
					.giftcardPurchaseTime(rs.getTimestamp("giftcard_purchase_time"))
				.build();
	}
}

package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.GiftcardDto;


@Component
public class GiftcardMapper implements RowMapper<GiftcardDto>{
	@Override
	public GiftcardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//		GiftcardDto giftcardDto = new GiftcardDto();
//		giftcardDto.setGiftcardNo(rs.getInt("giftcard_no"));
//		giftcardDto.setGiftcardName(rs.getString("giftcard_name"));
//		giftcardDto.setGiftcardContent(rs.getString("giftcard_content"));
//		giftcardDto.setGiftcardCharge(rs.getInt("giftcard_charge"));
//		giftcardDto.setGiftcardPrice(rs.getInt("giftcard_price"));
//		return giftcardDto;
		
//		Builder 패턴(롬복에서 제공)
		return GiftcardDto.builder()
					.giftcardNo(rs.getInt("giftcard_no"))
					.giftcardName(rs.getString("giftcard_name"))
					.giftcardContent(rs.getString("giftcard_content"))
					.giftcardCharge(rs.getInt("giftcard_charge"))
					.giftcardPrice(rs.getInt("giftcard_price"))
				.build();
	}
}
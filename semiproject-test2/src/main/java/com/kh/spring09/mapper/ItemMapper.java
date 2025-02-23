package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.ItemDto;


@Component
public class ItemMapper implements RowMapper<ItemDto> {

	@Override
	public ItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ItemDto.builder()
				.itemNo(rs.getInt("item_no"))
				.itemName(rs.getString("item_name"))
				.itemPrice(rs.getInt("item_price"))
				.itemRegist(rs.getTimestamp("item_regist"))
				.build();
	}
}




//@Component
//public class ItemMapper implements RowMapper<ItemDto>{
//	@Override
//	public ItemDto mapRow(ResultSet rs, int idx) throws SQLException {
//		ItemDto itemDto = new ItemDto();
//		itemDto.setItemNo(rs.getInt("item_no"));
//		itemDto.setItemName(rs.getString("item_name"));
//		itemDto.setItemType(rs.getString("item_type"));
//		itemDto.setItemPrice(rs.getInt("item_price"));
//		//itemDto.setItemDiscountRate(rs.getFloat("item_discount_rate"));//비추천
//		//itemDto.setItemDiscountRate((Float)rs.getObject("item_discount_rate"));//비추천 (운이 좋으면 형태가 맞음)
//		itemDto.setItemDiscountRate(rs.getObject("item_discount_rate", Float.class));//추천 (형태를 지정해서 추출)
//		itemDto.setItemQty(rs.getInt("item_qty"));
//		itemDto.setItemEarly(rs.getString("item_early"));
//		return itemDto;
//	}
//}
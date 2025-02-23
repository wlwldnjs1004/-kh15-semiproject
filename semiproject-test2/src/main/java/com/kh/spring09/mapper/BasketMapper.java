package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.BasketDto;

@Component
public class BasketMapper implements RowMapper<BasketDto> {

	@Override
	public BasketDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return BasketDto.builder()
				.basketNo(rs.getInt("basket_no"))
				.basketUser(rs.getString("basket_user"))
				.basketItem(rs.getInt("basket_item"))
				.basketTime(rs.getTimestamp("basket_time"))
				.basketQty(rs.getInt("basket_qty"))
				.build();
	}

}

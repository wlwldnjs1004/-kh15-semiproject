package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.PhoneDto;


@Component
public class PhoneMapper implements RowMapper<PhoneDto>{
	@Override
	public PhoneDto mapRow(ResultSet rs, int idx) throws SQLException {
		PhoneDto phoneDto = new PhoneDto();
		phoneDto.setPhoneNo(rs.getInt("phone_no"));
		phoneDto.setPhoneName(rs.getString("phone_name"));
		phoneDto.setPhoneTelecom(rs.getString("phone_telecom"));
		phoneDto.setPhonePrice(rs.getInt("phone_price"));
		//Integer는 getObject() 사용
		phoneDto.setPhoneContract(rs.getObject("phone_contract", Integer.class));
		return phoneDto;
	}
}

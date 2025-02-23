package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.CountryDto;

@Component//DB 가 아닌 작업 한 가지만 하는 도구
public class CountryMapper implements RowMapper<CountryDto>{

	@Override
	public CountryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryDto countryDto=new CountryDto();
		countryDto.setCountryNo(rs.getInt("country_no"));
		countryDto.setCountryName(rs.getString("country_name"));
		countryDto.setCountryCapital(rs.getString("country_capital"));
		countryDto.setCountryPopulation(rs.getLong("country_population"));
		return countryDto;
	}

	
	
}

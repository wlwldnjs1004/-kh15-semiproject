package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.ClientDto;

@Component
public class ClientMapper implements RowMapper<ClientDto>{

	@Override
	public ClientDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ClientDto.builder()
				.clientId(rs.getString("client_id"))
				.clientPassword(rs.getString("client_password"))
				.clientNickname(rs.getString("client_nickname"))
				.clientGrade(rs.getString("client_grade"))
				.clientJoin(rs.getTimestamp("client_join"))
				.clientPoint(rs.getInt("client_point"))
				.build();
	}

}

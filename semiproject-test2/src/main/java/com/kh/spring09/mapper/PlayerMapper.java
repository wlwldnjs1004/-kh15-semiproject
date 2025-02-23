package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.PlayerDto;


@Component
public class PlayerMapper implements RowMapper<PlayerDto>{
	@Override
	public PlayerDto mapRow(ResultSet rs, int idx) throws SQLException {
		PlayerDto playerDto = new PlayerDto();
		playerDto.setPlayerNo(rs.getInt("player_no"));
		playerDto.setPlayerName(rs.getString("player_name"));
		playerDto.setPlayerEvent(rs.getString("player_event"));
		playerDto.setPlayerType(rs.getString("player_type"));
		playerDto.setPlayerGoldMedal(rs.getInt("player_gold_medal"));
		playerDto.setPlayerSilverMedal(rs.getInt("player_silver_medal"));
		playerDto.setPlayerBronzeMedal(rs.getInt("player_bronze_medal"));
		return playerDto;
	}
}

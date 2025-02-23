package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.GameUserDto;


@Component
public class GameUserMapper implements RowMapper<GameUserDto>{
	@Override
	public GameUserDto mapRow(ResultSet rs, int idx) throws SQLException {
		GameUserDto gameUserDto = new GameUserDto();
		gameUserDto.setGameUserNo(rs.getInt("game_user_no"));
		gameUserDto.setGameUserId(rs.getString("game_user_id"));
		gameUserDto.setGameUserJob(rs.getString("game_user_job"));
		gameUserDto.setGameUserLevel(rs.getInt("game_user_level"));
		gameUserDto.setGameUserMoney(rs.getInt("game_user_money"));
		return gameUserDto;
	}
}


package com.kh.spring09.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.mapper.StatusMapper;
import com.kh.spring09.vo.StatusVO;

//테이블 종류와 상관없이 통계 현황 관련된 조회를 전담하는 도구
@Repository
public class StatusDao {
	@Autowired
	private StatusMapper statusMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//포켓몬 현황
	public List<StatusVO> pokemon() {
		String sql = "select pokemon_type key, count(*) value from pokemon "
						+ "group by pokemon_type "
						+ "order by value desc, key asc";
		return jdbcTemplate.query(sql, statusMapper);
	}
	//게임유저 현황
	public List<StatusVO> gameUser() {
		String sql = "select game_user_job key, count(*) value from game_user "
						+ "group by game_user_job "
						+ "order by value desc, key asc";
		return jdbcTemplate.query(sql, statusMapper);
	}
	
	//등급별 회원현황
	public List<StatusVO> member() {
		String sql = "select member_level key, count(*) value from member "
						+"group by member_level order by value desc, key asc";
		return jdbcTemplate.query(sql, statusMapper);
	}
	public List<StatusVO> memberJoin() {
		String sql = "select to_char(member_join, 'YYYY-MM') key, count(*) value "
				+ "from member "
				+ "group by to_char(member_join, 'YYYY-MM') "
				+ "order by key desc";
		return jdbcTemplate.query(sql, statusMapper);
	}
	public List<StatusVO> boardWrite() {
		String sql = "select to_char(board_wtime, 'YYYY-MM') key, count(*) value "
				+ "from board "
				+ "group by to_char(board_wtime, 'YYYY-MM') "
				+ "order by key desc";
		return jdbcTemplate.query(sql, statusMapper);
	}
}


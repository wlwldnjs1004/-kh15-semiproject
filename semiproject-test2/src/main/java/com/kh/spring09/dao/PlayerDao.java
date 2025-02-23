package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.PlayerDto;
import com.kh.spring09.mapper.PlayerMapper;


@Repository
public class PlayerDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PlayerMapper playerMapper;

	//등록 메소드
	public void insert(PlayerDto playerDto) {
		String sql = "insert into player("
							+ "player_no, player_name, player_event, player_type,"
							+ "player_gold_medal, player_silver_medal, player_bronze_medal"
						+ ") "
						+ "values(player_seq.nextval, ?, ?, ?, ?, ?, ?)";
		Object[] data = {
			playerDto.getPlayerName(), playerDto.getPlayerEvent(),
			playerDto.getPlayerType(), playerDto.getPlayerGoldMedal(),
			playerDto.getPlayerSilverMedal(), playerDto.getPlayerBronzeMedal()
		};
		jdbcTemplate.update(sql, data);
	}
	
	//시퀸스 등록
	public int sequence() {
		String sql="select player_seq.nextval from dual ";
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	public void inesrt2(PlayerDto playerDto) {
		String sql = "insert into player("
				+ "player_no, player_name, player_event, player_type,"
				+ "player_gold_medal, player_silver_medal, player_bronze_medal"
			+ ") "
			+ "values(?, ?, ?, ?, ?, ?, ?)";
Object[] data = {
		playerDto.getPlayerNo(),
		playerDto.getPlayerName(),
		playerDto.getPlayerEvent(),
		playerDto.getPlayerType(), 
		playerDto.getPlayerGoldMedal(),
		playerDto.getPlayerSilverMedal(), 
		playerDto.getPlayerBronzeMedal()
};
jdbcTemplate.update(sql, data);
}
	//이미지 등록
	public void connect(int playerNo,int attachmentNo) {
		String sql="insert into player_picture("
				+ "player_no, attachment_no "
				+ ") values(?,?)";
		Object[] data= {playerNo,attachmentNo};
	}
	
	//이미지 조회
	public int findAttachment(int playerNo) {
		String sql="select atachment_no from player_picture"
				+ "where player_no=? ";
		Object[] data= {playerNo};
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	
	//수정 메소드
	public boolean update(PlayerDto playerDto) {
		String sql = "update player "
						+ "set player_name=?, player_event=?, player_type=?, "
							+ "player_gold_medal=?, player_silver_medal=?, "
							+ "player_bronze_medal=? "
						+ "where player_no=?";
		Object[] data = {
			playerDto.getPlayerName(), playerDto.getPlayerEvent(),
			playerDto.getPlayerType(), playerDto.getPlayerGoldMedal(),
			playerDto.getPlayerSilverMedal(), playerDto.getPlayerBronzeMedal(),
			playerDto.getPlayerNo()
		};
//		int rows = jdbcTemplate.update(sql, data);
//		return rows > 0;
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//삭제 메소드
	public boolean delete(int playerNo) { 
		String sql = "delete player where player_no=?";
		Object[] data = {playerNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//조회 메소드
	public List<PlayerDto> selectList() {
		String sql = "select * from player";
		return jdbcTemplate.query(sql, playerMapper);
	}
	
	private Map<String, String> columnExamples = Map.of(
		"선수명", "player_name",
		"종목명", "player_event",
		"구분", "player_type"
	);
	
	public List<PlayerDto> selectList(String column, String keyword) {
		String columnName = columnExamples.get(column);
		if(columnName == null) throw new RuntimeException("항목 오류");
		
		String sql = "select * from player where instr(#1, ?) > 0 "
				+ "order by #1 asc, player_no asc";
		Object[] data = {keyword};
		return jdbcTemplate.query(sql, playerMapper, data);
	}
	
	//상세조회 메소드
	public PlayerDto selectOne(int playerNo) {
		String sql = "select * from player where player_no=?";
		Object[] data = {playerNo};
		List<PlayerDto> list = jdbcTemplate.query(sql, playerMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}
	
}



		
	

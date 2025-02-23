package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.BoardDto;

@Component
public class BoardMapper implements RowMapper<BoardDto> {

	@Override
	public BoardDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardDto boardDto=new BoardDto();
		boardDto.setBoardNo(rs.getInt("board_no"));
		boardDto.setBoardTitle(rs.getString("board_title"));
		boardDto.setBoardContent(rs.getString("board_content"));
		boardDto.setBoardWriter(rs.getString("board_writer"));
		boardDto.setBoardWtime(rs.getTimestamp("board_wtime"));
        boardDto.setBoardEtime(rs.getTimestamp("board_Etime")); 
		boardDto.setBoardLike(rs.getInt("board_like"));
		boardDto.setBoardRead(rs.getInt("board_read")); 
		boardDto.setBoardReply(rs.getInt("board_reply"));
//		boardDto.setMemberNickname(rs.getString("member_nickname"));//별도 필드?
		boardDto.setBoardGroup(rs.getInt("board_group"));
		boardDto.setBoardTarget(rs.getObject("board_target",Integer.class));
		boardDto.setBoardDepth(rs.getInt("board_depth"));
		
		return boardDto;
	}

}

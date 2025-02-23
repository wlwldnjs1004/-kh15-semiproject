package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.BoardDto;
import com.kh.spring09.dto.BoardListViewDto;

@Component
public class BoardListViewMapper implements RowMapper<BoardListViewDto>{
	@Override
	public BoardListViewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardListViewDto boardListViewDto = new BoardListViewDto();
		//board 테이블 부분
		boardListViewDto.setBoardNo(rs.getInt("board_no"));
		boardListViewDto.setBoardTitle(rs.getString("board_title"));
		boardListViewDto.setBoardWriter(rs.getString("board_writer"));
		boardListViewDto.setBoardWtime(rs.getTimestamp("board_wtime"));
		boardListViewDto.setBoardEtime(rs.getTimestamp("board_etime"));
		boardListViewDto.setBoardLike(rs.getInt("board_like"));
		boardListViewDto.setBoardRead(rs.getInt("board_read"));
		boardListViewDto.setBoardReply(rs.getInt("board_reply"));
		boardListViewDto.setBoardGroup(rs.getInt("board_group"));
		boardListViewDto.setBoardTarget(rs.getObject("board_target", Integer.class));
		boardListViewDto.setBoardDepth(rs.getInt("board_depth"));
		//상위글 부분
		boardListViewDto.setTargetNo(rs.getInt("target_no"));
		boardListViewDto.setTargetTitle(rs.getString("target_title"));
		//member 부분
		boardListViewDto.setMemberId(rs.getString("member_id"));
		boardListViewDto.setMemberPw(rs.getString("member_pw"));
		boardListViewDto.setMemberNickname(rs.getString("member_nickname"));
		boardListViewDto.setMemberBirth(rs.getString("member_birth"));
		boardListViewDto.setMemberContact(rs.getString("member_contact"));
		boardListViewDto.setMemberEmail(rs.getString("member_email"));
		boardListViewDto.setMemberLevel(rs.getString("member_level"));
		boardListViewDto.setMemberPoint(rs.getInt("member_point"));
		boardListViewDto.setMemberPost(rs.getString("member_post"));
		boardListViewDto.setMemberAddress1(rs.getString("member_address1"));
		boardListViewDto.setMemberAddress2(rs.getString("member_address2"));
		boardListViewDto.setMemberJoin(rs.getTimestamp("member_join"));
		boardListViewDto.setMemberLogin(rs.getTimestamp("member_login"));
		boardListViewDto.setMemberChange(rs.getTimestamp("member_change"));
		return boardListViewDto;
	}
}
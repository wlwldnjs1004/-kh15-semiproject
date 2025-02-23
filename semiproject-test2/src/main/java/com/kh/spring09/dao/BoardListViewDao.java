package com.kh.spring09.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.BoardListViewDto;
import com.kh.spring09.mapper.BoardListViewMapper;
import com.kh.spring09.vo.PageVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.BoardListViewDto;
import com.kh.spring09.mapper.BoardListViewMapper;
import com.kh.spring09.vo.PageVO;

@Repository
public class BoardListViewDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private BoardListViewMapper boardListViewMapper;
	
	public List<BoardListViewDto> selectList(PageVO pageVO) {
		if(pageVO.isList()) {
			String sql = 
				"select * from ("
				+ "select rownum rn, TMP.* from ("
					+ "select * from board_list_view "
					+ "connect by prior board_no=board_target "
					+ "start with board_target is null "
					+ "order siblings by board_group desc, board_no asc"
				+ ")TMP"
			+ ") where rn between ? and ?";
			Object[] data = {
				pageVO.getStartRownum(), pageVO.getFinishRownum()
			};
			return jdbcTemplate.query(sql, boardListViewMapper, data);
		}
		else {//검색
			String sql = 
				"select * from ( "
				+ "select rownum rn, TMP.* from ( "
					+ "select * from board_list_view "
					+ "where instr(#1, ?) > 0 "
					+ "connect by prior board_no=board_target "
					+ "start with board_target is null "
					+ "ORDER SIBLINGS BY board_group DESC, board_no asc "
				+ ")TMP "
			+ ") where rn between ? and ?";
			sql = sql.replace("#1", pageVO.getColumn());
			Object[] data = {
				pageVO.getKeyword(), 
				pageVO.getStartRownum(), pageVO.getFinishRownum()
			};
			return jdbcTemplate.query(sql, boardListViewMapper, data);
		}
	}
}
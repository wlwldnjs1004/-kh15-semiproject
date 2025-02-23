package com.kh.spring09.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.BoardDto;
import com.kh.spring09.error.NoPermissionException;
import com.kh.spring09.mapper.BoardListMapper;
import com.kh.spring09.mapper.BoardMapper;
import com.kh.spring09.vo.PageVO;

@Repository
public class BoardDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private BoardListMapper boardListMapper;

	public List<BoardDto> selectList() {
		String sql = "select "
							+ "board_no, board_title, "
							+ "board_writer, board_wtime, board_etime, "
							+ "board_like, board_read, board_reply, "
							+ "board_group, board_target, board_depth "
						+ "from board "
							+ "connect by prior board_no=board_target "
							+ "start with board_target is null "
							+ "order siblings by board_group desc, board_no asc";
		return jdbcTemplate.query(sql, boardListMapper);
	}
	public List<BoardDto> selectListByPaging(int page, int size) {
		int begin = page * size - (size-1);
		int end = page * size;
		
		String sql = "select * from ("
								+ "select rownum rn , TMP.* from ("
										+ "select "
											+ "board_no, board_title, "
											+ "board_writer, board_wtime, board_etime, "
											+ "board_like, board_read, board_reply, "
											+ "board_group, board_target, board_depth "
										+ "from board "
										+ "connect by prior board_no=board_target "
										+ "start with board_target is null "
										+ "order siblings by board_group desc, board_no asc"
								+ ")TMP"
						+ ") where rn between ? and ?";
		Object[] data = {begin, end};
		return jdbcTemplate.query(sql, boardListMapper, data);
	}
	
	
	
	public List<BoardDto> selectList(String column, String keyword) {
		//검색 항목 검사
		switch(column) {
		case "board_writer":
		case "board_title":
			break;
		default:
			throw new NoPermissionException("검색할 수 없는 항목");
		}
		
		String sql = "select "
							+ "board_no, board_title, "
							+ "board_writer, board_wtime, board_etime, "
							+ "board_like, board_read, board_reply, "
							+ "board_group, board_target, board_depth "
						+ "from board "
						+ "where instr(#1, ?) > 0 "
						+ "connect by prior board_no=board_target "
						+ "start with board_target is null "
						+ "order siblings by board_group desc, board_no asc";
		sql = sql.replace("#1", column);
		Object[] data = {keyword};
		return jdbcTemplate.query(sql, boardListMapper, data);
	}
	public List<BoardDto> selectListByPaging(
			String column, String keyword, int page, int size) {
		int begin = page * size - (size-1);
		int end = page * size;
		
		//검색 항목 검사
		switch(column) {
		case "board_writer":
		case "board_title":
			break;
		default:
			throw new NoPermissionException("검색할 수 없는 항목");
		}
		
		String sql = "select * from ("
							+ "select rownum rn, TMP.* from ("
								+ "select "
									+ "board_no, board_title, "
									+ "board_writer, board_wtime, board_etime, "
									+ "board_like, board_read, board_reply, "
									+ "board_group, board_target, board_depth "
								+ "from board "
								+ "where instr(#1, ?) > 0 "
								+ "connect by prior board_no=board_target "
								+ "start with board_target is null "
								+ "order siblings by board_group desc, board_no asc"
							+ ") TMP"
						+ ") where rn between ? and ?";
		sql = sql.replace("#1", column);
		Object[] data = {keyword, begin, end};
		return jdbcTemplate.query(sql, boardListMapper, data);
	}
	
	//페이징용 통합 조회 메소드
	public List<BoardDto> selectListByPaging(PageVO pageVO) {
		if(pageVO.isList()) {//목록이라면
			return selectListByPaging(pageVO.getPage(), pageVO.getSize());
		}
		else {//검색이라면
			return selectListByPaging(
				pageVO.getColumn(), pageVO.getKeyword(),
				pageVO.getPage(), pageVO.getSize()
			);
		}
	}
	
	//카운트 조회 명령
	public int count() {
		String sql = "select count(*) from board";
		return jdbcTemplate.queryForObject(sql, int.class);
//		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	public int count(String column, String keyword) {
		String sql = "select count(*) from board where instr(#1, ?) > 0";
		sql = sql.replace("#1", column);
		Object[] data = {keyword};
		return jdbcTemplate.queryForObject(sql, int.class, data);
	}
	
	public int count(PageVO pageVO) {
		if(pageVO.isList())
			return count();
		else 
			return count(pageVO.getColumn(), pageVO.getKeyword());
	}
	
	
	public BoardDto selectOne(int boardNo) {
		String sql = "select * from board where board_no=?";
		Object[] data = {boardNo};
		List<BoardDto> list = jdbcTemplate.query(sql, boardMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}
	
	public void insert(BoardDto boardDto) {
		String sql = "insert into board("
							+ "board_no, board_title, "
							+ "board_content, board_writer"
						+ ") "
						+ "values(board_seq.nextval, ?, ?, ?)";
		Object[] data = {
			boardDto.getBoardTitle(), 
			boardDto.getBoardContent(),
			boardDto.getBoardWriter()
		};
		jdbcTemplate.update(sql, data);
	}
	//시퀀스 발급과 등록을 분리
	public int sequence() {
		String sql = "select board_seq.nextval from dual";//dual=임시테이블
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	public void insert2(BoardDto boardDto) {
		String sql = "insert into board("
							+ "board_no, board_title, "
							+ "board_content, board_writer, "
							+ "board_group, board_target, board_depth"
						+ ") "
						+ "values(?, ?, ?, ?, ?, ?, ?)";
		Object[] data = {
			boardDto.getBoardNo(), boardDto.getBoardTitle(),
			boardDto.getBoardContent(), boardDto.getBoardWriter(),
			boardDto.getBoardGroup(), boardDto.getBoardTarget(),
			boardDto.getBoardDepth()
		};
		jdbcTemplate.update(sql, data);
	}
	
	public boolean delete(int boardNo) {
		String sql = "delete board where board_no=?";
		Object[] data = {boardNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	public boolean update(BoardDto boardDto) {
		String sql = "update board "
						+ "set board_title=?, board_content=?, "
								+ "board_etime=systimestamp "
						+ "where board_no=?";
		Object[] data = {
			boardDto.getBoardTitle(), boardDto.getBoardContent(),
			boardDto.getBoardNo()
		};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//조회수 1 증가 메소드
	public boolean updateBoardRead(int boardNo) {
		String sql = "update board "
						+ "set board_read=board_read+1 "
						+ "where board_no=?";
		Object[] data = {boardNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	
	//////////////////////////////////////////////////////////////////
	// 게시글 좋아요 관련 처리 기능
	//////////////////////////////////////////////////////////////////
	
	// 좋아요 설정
	public void insertBoardLike(String memberId, int boardNo) {
		String sql = "insert into board_like(member_id, board_no) values(?, ?)";
		Object[] data = {memberId, boardNo};
		jdbcTemplate.update(sql, data);
	}
	
	// 좋아요 해제
	public void deleteBoardLike(String memberId, int boardNo) {
		String sql = "delete board_like where member_id=? and board_no=?";
		Object[] data = {memberId, boardNo};
		jdbcTemplate.update(sql, data);
	}
	
	// 좋아요 검사
	public boolean checkBoardLike(String memberId, int boardNo) {
		String sql = "select count(*) from board_like "
						+ "where member_id=? and board_no=?";
		Object[] data = {memberId, boardNo};
		return jdbcTemplate.queryForObject(sql, int.class, data) > 0;
	}
	
	//좋아요 개수
	public int countBoardLike(int boardNo) {
		String sql = "select count(*) from board_like where board_no=?";
		Object[] data = {boardNo};
		return jdbcTemplate.queryForObject(sql, int.class, data);
	}
	
	//좋아요 개수를 갱신하는 메소드
	public boolean updateBoardLike(int boardNo, int count) {
		String sql = "update board set board_like = ? where board_no = ?";
		Object[] data = {count, boardNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	public boolean updateBoardLike(int boardNo) {
		String sql = "update board set board_like = ("
							+ "select count(*) from board_like where board_no = ?"
						+ ") where board_no = ?";
		Object[] data = {boardNo, boardNo};//홀더 개수와 순서에 맞게
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//댓글 수 갱신 메소드
	public boolean updateBoardReply(int boardNo) {
		String sql = "update board set board_reply = ("
							+ "select count(*) from reply where reply_origin = ?"
						+ ") where board_no = ?";
		Object[] data = {boardNo, boardNo};//홀더 개수와 순서에 맞게
		return jdbcTemplate.update(sql, data) > 0;
	}
}








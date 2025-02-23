package com.kh.spring09.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.ReplyDto;
import com.kh.spring09.mapper.ReplyMapper;

@Repository
public class ReplyDao {

	@Autowired
	private ReplyMapper replyMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//등록(+시퀀스)
	public int sequence() {
		String sql = "select reply_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	public void insert(ReplyDto replyDto) {
		String sql = "insert into reply("
								+ "reply_no, reply_writer, "
								+ "reply_origin, reply_content"
						+ ") "
						+ "values(?, ?, ?, ?)";
		Object[] data = {
			replyDto.getReplyNo(), replyDto.getReplyWriter(),
			replyDto.getReplyOrigin(), replyDto.getReplyContent()
		};
		jdbcTemplate.update(sql, data);
	}
	public List<ReplyDto> selectList(int replyOrigin) {
		String sql = "select * from reply "
						+ "where reply_origin=? "
						+ "order by reply_no asc";
		Object[] data = {replyOrigin};
		return jdbcTemplate.query(sql, replyMapper, data);
	}
	public boolean delete(int replyNo) {
		String sql = "delete reply where reply_no = ?";
		Object[] data = {replyNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	public ReplyDto selectOne(int replyNo) {
		String sql = "select * from reply where reply_no = ?";
		Object[] data = {replyNo};
		List<ReplyDto> list = jdbcTemplate.query(sql, replyMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}
	
	public boolean update(ReplyDto replyDto) {
		String sql = "update reply "
						+ "set reply_content=?, reply_etime=systimestamp "
						+ "where reply_no=?";
		Object[] data = {
				replyDto.getReplyContent(),
				replyDto.getReplyNo()
		};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
}
package com.kh.spring09.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.GiftcardDto;
import com.kh.spring09.mapper.GiftcardMapper;

@Repository
public class GiftcardDao {

	@Autowired
	private GiftcardMapper giftcardMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//시퀀스 생성 + 등록
	public int sequence() {
		String sql = "select giftcard_seq.nextval from dual";
		return jdbcTemplate.queryForObject(sql, int.class);
	}
	public void insert(GiftcardDto giftcardDto) {
		String sql = "insert into giftcard("
							+ "giftcard_no, giftcard_name, giftcard_content,"
							+ "giftcard_charge, giftcard_price"
						+ ") values(?, ?, ?, ?, ?)";
		Object[] data = {
			giftcardDto.getGiftcardNo(), giftcardDto.getGiftcardName(),
			giftcardDto.getGiftcardContent(), giftcardDto.getGiftcardCharge(),
			giftcardDto.getGiftcardPrice()
		};
		jdbcTemplate.update(sql, data);
	}
	
	public void connect(int giftcardNo, int attachmentNo) {
		String sql = "insert into giftcard_image(giftcard_no, attachment_no) values(?, ?)";
		Object[] data = {giftcardNo, attachmentNo};
		jdbcTemplate.update(sql, data);
	}
	
	
	public List<GiftcardDto> selectList() {
		String sql = "select * from giftcard "
						+ "order by giftcard_price asc, "
								+ "giftcard_charge desc, giftcard_no asc";
		return jdbcTemplate.query(sql, giftcardMapper);
	}
	
	public int findAttachment(int giftcardNo) {
		String sql = "select attachment_no from giftcard_image where giftcard_no=?";
		Object[] data = {giftcardNo};
		return jdbcTemplate.queryForObject(sql, int.class, data);
	}
	
	
	public GiftcardDto selectOne(int giftcardNo) {
		String sql = "select * from giftcard where giftcard_no=?";
		Object[] data = {giftcardNo};
		List<GiftcardDto> list = jdbcTemplate.query(sql, giftcardMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}
	
}



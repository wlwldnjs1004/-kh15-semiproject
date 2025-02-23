package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.PhoneDto;
import com.kh.spring09.error.NoPermissionException;
import com.kh.spring09.mapper.PhoneMapper;
import com.kh.spring09.vo.PageVO;


@Repository
public class PhoneDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PhoneMapper phoneMapper;

	//등록 메소드
	public void insert(PhoneDto phoneDto) {
		String sql = "insert into phone(phone_no, phone_name, phone_telecom, phone_price, phone_contract) "
				+ "values(phone_seq.nextval, ?, ?, ?, ?)";
		Object[] data = {
				phoneDto.getPhoneName(), phoneDto.getPhoneTelecom(),
				phoneDto.getPhonePrice(), phoneDto.getPhoneContract()
		};
		jdbcTemplate.update(sql, data);
	}
	
	//수정 메소드
	public boolean update(PhoneDto phoneDto) {
		String sql = "update phone set "
							+ "phone_name=?, phone_telecom=?, "
							+ "phone_price=?, phone_contract=? "
						+ "where phone_no=?";
		Object[] data = {
			phoneDto.getPhoneName(), phoneDto.getPhoneTelecom(),
			phoneDto.getPhonePrice(), phoneDto.getPhoneContract(),
			phoneDto.getPhoneNo()
		};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//삭제 메소드
	public boolean delete(int phoneNo) {
		String sql = "delete phone where phone_no=?";
		Object[] data = {phoneNo};
		return jdbcTemplate.update(sql, data) > 0;
	}
	
	//조회 메소드
	public List<PhoneDto> selectList() {
		String sql = "select "
							+ "phone_no, phone_name, "
							+ "phone_telecom, phone_price, phone_contract "
						+ "from phone order by phone_no desc";
		return jdbcTemplate.query(sql, phoneMapper);
	}
	public List<PhoneDto> selectListByPaging(int page, int size) {
		int begin = page * size - (size-1);
		int end = page * size;
		
		String sql = "select * from ("
								+ "select rownum rn , TMP.* from ("
										+ "select "
											+ "phone_no, phone_name, phone_telecom, phone_price, phone_contract "
										+ "from phone order by phone_no desc"
								+ ")TMP"
						+ ") where rn between ? and ?";
		Object[] data = {begin, end};
		return jdbcTemplate.query(sql, phoneMapper, data);
	}
	
	public List<PhoneDto> selectList(String column, String keyword) {
		//검색 항목 검사
		switch(column) {
		case "phone_name":
		case "phone_telecom":
			break;
		default:
			throw new NoPermissionException("검색할 수 없는 항목");
		}
		
		String sql = "select "
							+ "phone_no, phone_name, "
							+ "phone_telecom, phone_price, phone_contract "
						+ "from country "
						+ "where instr(#1, ?) > 0 "
						+ "order by phone_no desc";
		sql = sql.replace("#1", column);
		Object[] data = {keyword};
		return jdbcTemplate.query(sql, phoneMapper, data);
	}
	public List<PhoneDto> selectListByPaging(
			String column, String keyword, int page, int size) {
		int begin = page * size - (size-1);
		int end = page * size;
		
		//검색 항목 검사
		switch(column) {
		case "phone_name":
		case "phone_telecom":
			break;
		default:
			throw new NoPermissionException("검색할 수 없는 항목");
		}
		
		String sql = "select * from ("
							+ "select rownum rn, TMP.* from ("
								+ "select "
									+ "phone_no, phone_name, "
									+ "phone_telecom, phone_price, phone_contract  "
								+ "from phone "
								+ "where instr(#1, ?) > 0 "
								+ "order by phone_no desc"
							+ ") TMP"
						+ ") where rn between ? and ?";
		sql = sql.replace("#1", column);
		Object[] data = {keyword, begin, end};
		return jdbcTemplate.query(sql, phoneMapper, data);
	}
	//카운터 조회
	public int count() {
		String sql = "select count(*) from phone";
		return jdbcTemplate.queryForObject(sql, int.class);
//		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	public int count(PageVO pageVO) {
	    if (pageVO.isList()) {
	        return count();
	    } else {
	        return count(pageVO.getColumn(), pageVO.getKeyword()); // 문제 발생
	    }
	}
	public int count(String column, String keyword) {
	    // 검색 조건 확인
	    switch (column) {
	        case "phone_name":
	        case "phone_telecom":
	            break;
	        default:
	            throw new NoPermissionException("검색할 수 없는 항목입니다.");
	    }

	    // SQL 쿼리 작성 및 실행
	    String sql = "select count(*) from phone where instr(#1, ?) > 0";
	    sql = sql.replace("#1", column); // 컬럼명 동적 교체
	    Object[] params = {keyword};
	    return jdbcTemplate.queryForObject(sql, int.class, params);
	}
	
	public List<PhoneDto> selectListByPaging(PageVO pageVO) {
	    if (pageVO.isList()) {
	        return selectListByPaging(pageVO.getPage(), pageVO.getSize());
	    } else {
	        return selectListByPaging(pageVO.getColumn(), pageVO.getKeyword(), pageVO.getPage(), pageVO.getSize());
	    }
	}	
	
	
	private Map<String, String> columnExamples = Map.of(
		"기종", "phone_name",
		"통신사", "phone_telecom"
	);
	
	
	//상세조회 메소드
	public PhoneDto selectOne(int phoneNo) {
		String sql = "select * from phone where phone_no = ?";
		Object[] data = {phoneNo};
		List<PhoneDto> list = jdbcTemplate.query(sql, phoneMapper, data);
		return list.isEmpty() ? null : list.get(0);
	}
	
}


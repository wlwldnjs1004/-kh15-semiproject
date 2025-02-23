package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.MenuDto;
import com.kh.spring09.error.NoPermissionException;
import com.kh.spring09.mapper.MenuMapper;
import com.kh.spring09.vo.PageVO;



@Repository
public class MenuDao {


	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void insert(MenuDto menuDto) {
		   
		
	    String sql = "insert into menu("
	            + "menu_no,menu_name,menu_type,menu_price,menu_event"
	            + ") values (menu_seq.nextval, ?, ?, ?, ?)";
	    Object[] data = {
	    		menuDto.getMenuName(),
	    	    menuDto.getMenuType(),
	    		menuDto.getMenuPrice(),
	    		menuDto.getMenuEvent(),
	    };
	    jdbcTemplate.update(sql, data);
	}

	public boolean update( MenuDto  menuDto) { // 수정된 메소드 이름
	    String sql = "update  menu "
	            + "set  menu_name = ?,  menu_type = ? , menu_price = ? ,  menu_event = ? "
	            + "where  menu_no = ?";
	    Object[] data = {
	    		menuDto.getMenuName(),
	    		menuDto.getMenuType(),
	    		menuDto.getMenuPrice(),
	    		menuDto.getMenuEvent(),
	    		menuDto.getMenuNo()
	    };
	    int rows = jdbcTemplate.update(sql, data);
	    return rows > 0;
	}
	public boolean delete(int menuNo) {
		String sql = "delete menu where menu_no = ? ";
		Object[] data= {menuNo};
		int rows = jdbcTemplate.update(sql,data);
		return jdbcTemplate.update(sql, data)> 0;
	}


	public List<MenuDto> selectList() {
		String sql = "select "
							+ "menu_no, menu_name, "
							+ "menu_type, menu_price, menu_event "
						+ "from menu order by menu_no desc";
		return jdbcTemplate.query(sql, menuMapper);
	}
	public List<MenuDto> selectListByPaging(int page, int size) {
		int begin = page * size - (size-1);
		int end = page * size;
		
		String sql = "select * from ("
								+ "select rownum rn , TMP.* from ("
										+ "select "
											+ "menu_no, menu_name, menu_type, menu_price, menu_event "
										+ "from menu order by menu_no desc"
								+ ")TMP"
						+ ") where rn between ? and ?";
		Object[] data = {begin, end};
		return jdbcTemplate.query(sql, menuMapper, data);
	}
	
	public List<MenuDto> selectList(String column, String keyword) {
		//검색 항목 검사
		switch(column) {
		case "menu_name":
		case "menu_type":
			break;
		default:
			throw new NoPermissionException("검색할 수 없는 항목");
		}
		
		String sql = "select "
							+ "menu_no, menu_name, "
							+ "menu_type, menu_price, menu_event "
						+ "from menu "
						+ "where instr(#1, ?) > 0 "
						+ "order by country_no desc";
		sql = sql.replace("#1", column);
		Object[] data = {keyword};
		return jdbcTemplate.query(sql, menuMapper, data);
	}
	public List<MenuDto> selectListByPaging(
			String column, String keyword, int page, int size) {
		int begin = page * size - (size-1);
		int end = page * size;
		
		//검색 항목 검사
		switch(column) {
		case "menu_name":
		case "menu_type":
			break;
		default:
			throw new NoPermissionException("검색할 수 없는 항목");
		}
		
		String sql = "select * from ("
							+ "select rownum rn, TMP.* from ("
								+ "select "
									+ "menu_no, menu_name, "
									+ "menu_type, menu_price, menu_event  "
								+ "from menu "
								+ "where instr(#1, ?) > 0 "
								+ "order by menu_no desc"
							+ ") TMP"
						+ ") where rn between ? and ?";
		sql = sql.replace("#1", column);
		Object[] data = {keyword, begin, end};
		return jdbcTemplate.query(sql, menuMapper, data);
	}
	//카운터 조회
	public int count() {
		String sql = "select count(*) from menu";
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
	        case "menu_name":
	        case "menu_type":
	            break;
	        default:
	            throw new NoPermissionException("검색할 수 없는 항목입니다.");
	    }

	    // SQL 쿼리 작성 및 실행
	    String sql = "select count(*) from menu where instr(#1, ?) > 0";
	    sql = sql.replace("#1", column); // 컬럼명 동적 교체
	    Object[] params = {keyword};
	    return jdbcTemplate.queryForObject(sql, int.class, params);
	}
	
	public List<MenuDto> selectListByPaging(PageVO pageVO) {
	    if (pageVO.isList()) {
	        return selectListByPaging(pageVO.getPage(), pageVO.getSize());
	    } else {
	        return selectListByPaging(pageVO.getColumn(), pageVO.getKeyword(), pageVO.getPage(), pageVO.getSize());
	    }
	}	
	
	

	private Map<String, String> columnExample=Map.of(
			"메뉴이름","menu_name",
			"메뉴분류","menu_type");




	public MenuDto selectOne(int menuNo) {
		String sql = "select * from menu where menu_no = ? ";
		Object[] data = {menuNo};
		List<MenuDto> list=jdbcTemplate.query(sql, menuMapper,data);
		return list.isEmpty() ? null : list.get(0);
	}




	}

package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.CountryDto;
import com.kh.spring09.dto.GameUserDto;
import com.kh.spring09.error.NoPermissionException;
import com.kh.spring09.mapper.CountryMapper;
import com.kh.spring09.vo.PageVO;

@Repository// 리파지토리 / 영원히 바뀌지 않는 도구
public class CountryDao {

	@Autowired
	private CountryMapper countryMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
		public void nisert(int countryNo,String countryName,String countryCapital,int countryPopulation) {
			
			String sql="insert into country("
					+ "country_no,country_name,country_capital,country_population"
					+ ")"
					+"values(country_seq.nextval,?,?,?)";
					Object[] data = {
							countryName,countryCapital,
							countryPopulation	};
			
			jdbcTemplate.update(sql, data);
		
		}
			
		public void insert(CountryDto countryDto) {
			String sql="insert into country("
					+ "country_no,country_name,country_capital,country_population"
					+ ")"
					+"values(country_seq.nextval,?,?,?)";
					Object[] data = {
							countryDto.getCountryName(),
							countryDto.getCountryCapital(),
							countryDto.getCountryPopulation()	};
			
			jdbcTemplate.update(sql, data);
		}
		
		//스퀸스+등록
		public int sequence() {
			String sql="select country_seq.nextval from dual ";
			return jdbcTemplate.queryForObject(sql, int.class);
			
		}
		public void inesrt2(CountryDto countryDto) {
			
			String sql="insert into country("
					+ "country_no, country_name, "
					+ "country_capital, country_population "
					+ ")"
					+"values(?,?,?,?)";
			Object[] data = {
					countryDto.getCountryNo(),
					countryDto.getCountryName(),
					countryDto.getCountryCapital(),
					countryDto.getCountryPopulation()	
					};
	
	jdbcTemplate.update(sql, data);
			
		}//이미지 연결
		public void connect(int countryNo, int attachmentNo) {
			String sql="insert into country_flag( "
					+ "country_no, attachment_no "
					+ ") values(?,?)";
			Object[]data= {countryNo, attachmentNo};
		jdbcTemplate.update(sql,data);
		}
		public int findAttachment(int countryNo) {
			String sql = "select attachment_no from country_flag  "
							+ "where country_no=?";
			Object[] data = {countryNo};
			return jdbcTemplate.queryForObject(sql, int.class, data);
		}
		
		
		public boolean update(CountryDto countryDto) { // 수정된 메소드 이름
	        String sql = "update country "
	                + "set country_name = ?, country_capital = ? ,country_population = ?"
	                + "where country_no = ?";
	        Object[] data = {
	        		countryDto.getCountryName(),
	        		countryDto.getCountryCapital(),
	        		countryDto.getCountryPopulation(),
	        		countryDto.getCountryNo()
	       	
	        };
	        int rows = jdbcTemplate.update(sql, data);
	        return rows > 0;
	    }
		
		public boolean delete(int countryNo) {
			String sql = "delete country where country_no = ?";
			Object[] data= {countryNo};
			int rows = jdbcTemplate.update(sql,data);
			
			return rows>0;
			
		}
		
			
			public List<CountryDto> selectList() {
				String sql = "select "
									+ "country_no, country_name, "
									+ "country_capital, country_population "
								+ "from country order by country_no desc";
				return jdbcTemplate.query(sql, countryMapper);
			}
			public List<CountryDto> selectListByPaging(int page, int size) {
				int begin = page * size - (size-1);
				int end = page * size;
				
				String sql = "select * from ("
										+ "select rownum rn , TMP.* from ("
												+ "select "
													+ "country_no, country_name, country_capital, country_population "
												+ "from country order by country_no desc"
										+ ")TMP"
								+ ") where rn between ? and ?";
				Object[] data = {begin, end};
				return jdbcTemplate.query(sql, countryMapper, data);
			}
			
			public List<CountryDto> selectList(String column, String keyword) {
				//검색 항목 검사
				switch(column) {
				case "country_name":
				case "country_capital":
					break;
				default:
					throw new NoPermissionException("검색할 수 없는 항목");
				}
				
				String sql = "select "
									+ "country_no, country_name, "
									+ "country_capital, country_population "
								+ "from country "
								+ "where instr(#1, ?) > 0 "
								+ "order by country_no desc";
				sql = sql.replace("#1", column);
				Object[] data = {keyword};
				return jdbcTemplate.query(sql, countryMapper, data);
			}
			public List<CountryDto> selectListByPaging(
					String column, String keyword, int page, int size) {
				int begin = page * size - (size-1);
				int end = page * size;
				
				//검색 항목 검사
				switch(column) {
				case "country_name":
				case "country_capital":
					break;
				default:
					throw new NoPermissionException("검색할 수 없는 항목");
				}
				
				String sql = "select * from ("
									+ "select rownum rn, TMP.* from ("
										+ "select "
											+ "country_no, country_name, "
											+ "country_capital, country_population  "
										+ "from country "
										+ "where instr(#1, ?) > 0 "
										+ "order by country_no desc"
									+ ") TMP"
								+ ") where rn between ? and ?";
				sql = sql.replace("#1", column);
				Object[] data = {keyword, begin, end};
				return jdbcTemplate.query(sql, countryMapper, data);
			}
			//카운터 조회
			public int count() {
				String sql = "select count(*) from country";
				return jdbcTemplate.queryForObject(sql, int.class);
//				return jdbcTemplate.queryForObject(sql, Integer.class);
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
			        case "country_name":
			        case "country_capital":
			            break;
			        default:
			            throw new NoPermissionException("검색할 수 없는 항목입니다.");
			    }

			    // SQL 쿼리 작성 및 실행
			    String sql = "select count(*) from country where instr(#1, ?) > 0";
			    sql = sql.replace("#1", column); // 컬럼명 동적 교체
			    Object[] params = {keyword};
			    return jdbcTemplate.queryForObject(sql, int.class, params);
			}
			
			public List<CountryDto> selectListByPaging(PageVO pageVO) {
			    if (pageVO.isList()) {
			        return selectListByPaging(pageVO.getPage(), pageVO.getSize());
			    } else {
			        return selectListByPaging(pageVO.getColumn(), pageVO.getKeyword(), pageVO.getPage(), pageVO.getSize());
			    }
			}	
			
			
			
			//검색 항목을 설정
			private Map<String, String> columnExample = Map.of(
				    "국가명", "country_name",
				    "수도명", "country_capital"
				);
	//검색 메소드
	 public CountryDto selectOne(int countryNo) {
		 String sql = "select * from country where country_no = ? ";
		 Object[] data = {countryNo};
		 List<CountryDto> list = jdbcTemplate.query(sql,countryMapper, data);
		 return list.isEmpty() ? null : list.get(0);
	 }
	}


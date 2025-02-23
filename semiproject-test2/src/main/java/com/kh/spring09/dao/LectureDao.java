package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.LectureDto;
import com.kh.spring09.mapper.LectureMapper;


@Repository
public class LectureDao {


	


		@Autowired
		private LectureMapper lectureMapper;
		
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		   public void insert(LectureDto lectureDto) {
		        String sql = "insert into lecture("
		        		+ "lecture_no,lecture_name,lecture_category,lecture_period,lecture_price,lecture_type"    
		                + ") values (lecture_seq.nextval, ?, ?, ?, ?, ?)";
		        Object[] data = {
		        		lectureDto.getLectureName(),
		        		lectureDto.getLectureCategory(),
		        		lectureDto.getLecturePeriod(),
		        		lectureDto.getLecturePrice(),
		        		lectureDto.getLectureType(),
		        };
		        jdbcTemplate.update(sql, data);
		    }
		   public boolean update( LectureDto  lectureDto) { // 수정된 메소드 이름
			    String sql = "update  lecture "
			            + " set  lecture_name = ?,  lecture_category = ? , lecture_period = ? ,  lecture_price = ? , lecture_type = ? "
			            + " where  lecture_no = ? ";
			    Object[] data = {
			    		lectureDto.getLectureName(),
			    		lectureDto.getLectureCategory(),
			    		lectureDto.getLecturePeriod(),
			    		lectureDto.getLecturePrice(),
			    		lectureDto.getLectureType(),
			    		lectureDto.getLectureNo(),
			    
			    };
			    int rows = jdbcTemplate.update(sql, data);
			    return rows > 0;
			}
			public boolean delete(int lectureNo) {
				String sql = "delete lecture where lecture_no = ? ";
				Object[] data= {lectureNo};
				int rows=jdbcTemplate.update(sql,data);
				return rows>0;
		
			}
			
				public List<LectureDto>selectList(){
					String sql="select * from lecture";
					return jdbcTemplate.query(sql, lectureMapper);

				}
			private Map<String, String> columnExample= Map.of(
				"강좌명","lecture_name",	
				"강좌분류","lecture_category");
			public List<LectureDto> selectList(String column,String keyword){
				String columnName=columnExample.get(column);
				if(columnName==null) {
					throw new RuntimeException("항목오류:유효하지 않는 검색");
					
				}
			String sql= " select * from lecture where "
					+ " instr( #1 , ? ) > 0 "
					+ " order by #1 asc, lecture_no asc ";
			sql=sql.replace("#1",columnName);
			
			Object[] data= {keyword};
			return jdbcTemplate.query(sql, lectureMapper,data);
			
			}
	public LectureDto selectOne(int lectureNo) {
		String sql = "select * from lecture where lecture_no=? ";
				 Object[] data= {lectureNo};
		List<LectureDto> list=jdbcTemplate.query(sql, lectureMapper,data);
		return list.isEmpty() ? null:list.get(0);
				}

	}
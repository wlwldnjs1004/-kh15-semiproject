package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring09.dto.GameUserDto;
import com.kh.spring09.error.NoPermissionException;
import com.kh.spring09.mapper.GameUserMapper;
import com.kh.spring09.vo.PageVO;


	@Repository
	public class GameUserDao {

		@Autowired
		private GameUserMapper gameUserMapper;
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		   public void insert(GameUserDto gameUserDto) {
		        String sql = "insert into game_user("
		        		+ "game_user_no , game_user_id , game_user_job , game_user_level , game_user_money "    
		                + ") values (game_user_seq.nextval, ?, ?, ?, ?) ";
		        Object[] data = {
		        		gameUserDto.getGameUserId(),
		        		gameUserDto.getGameUserJob(),
		        		gameUserDto.getGameUserLevel(),
		        		gameUserDto.getGameUserMoney(),
		        };
		        jdbcTemplate.update(sql, data);
		    }
		   //스퀸스 등록
		   public int sequence() {
			   String sql = "select game_user_seq.nextval from dual";
			   return jdbcTemplate.queryForObject(sql, int.class);
		   
		   }
		   //이미지 등록
		   public void inesrt2(GameUserDto gameUserDto) {
			   String sql =  "insert into game_user ( "
		        		+ "game_user_no, "
		        		+ "game_user_id, "
		        		+ "game_user_job, "
		        		+ "game_user_level, "
		        		+ "game_user_money "    
		                + ") "
		                + "values (?, ?, ?, ?, ?) ";
		        Object[] data = {
		        		gameUserDto.getGameUserNo(),
		        		gameUserDto.getGameUserId(),
		        		gameUserDto.getGameUserJob(),
		        		gameUserDto.getGameUserLevel(),
		        		gameUserDto.getGameUserMoney(),
		        };
		        jdbcTemplate.update(sql, data);
		    }
		   //유저 이미지 등록
		   public void connect(int gameUserNo,int attachmentNo) {
			   String sql="insert into game_user_profile("
			   		+ "game_user_no, attachment_no "
			   		+ ") values(?,?) ";
		   Object[] data= {gameUserNo, attachmentNo};
		   jdbcTemplate.update(sql,data);
		   
		   }
		   
		   public int findAttachment(int gameUserNo) {
			   String sql="select attachment_no from game_user_profile "
			   		+ "where game_user_no=? ";
			   Object[] data= {gameUserNo};
		   return jdbcTemplate.queryForObject(sql, int.class,data);
		   
		   }
		   
		   public boolean update(  GameUserDto   gameUserDto) { // 수정된 메소드 이름
			    String sql = "update  game_user"
			            + " set  game_user_id = ?,  game_user_job = ? , game_user_level = ? ,  game_user_money = ? "
			            + " where  game_user_no = ? ";
			    Object[] data = {
			    		gameUserDto.getGameUserId(),
			    		gameUserDto.getGameUserJob(),
			    		gameUserDto.getGameUserLevel(),
			    		gameUserDto.getGameUserMoney(),
			    		gameUserDto.getGameUserNo()
			    
			    };
			    int rows = jdbcTemplate.update(sql, data);
			    return rows > 0;
			}
		   
		   
		   
		   public boolean delete(int gameUserNo) {
				String sql = "delete game_user where game_user_no = ? ";
				Object[] data= {gameUserNo};
				int rows = jdbcTemplate.update(sql,data);
				return rows> 0;
				
			}
			
				public List<GameUserDto> selectList() {
					String sql = "select "
										+ "game_user_no, game_user_id, "
										+ "game_user_job, game_user_level, game_user_money "
									+ "from game_user order by game_user_no desc";
					return jdbcTemplate.query(sql, gameUserMapper);
				}
				public List<GameUserDto> selectListByPaging(int page, int size) {
					int begin = page * size - (size-1);
					int end = page * size;
					
					String sql = "select * from ("
											+ "select rownum rn , TMP.* from ("
													+ "select "
														+ "game_user_no, game_user_id, "
														+ "game_user_job, game_user_level, game_user_money "
													+ "from game_user order by game_user_no desc"
											+ ")TMP"
									+ ") where rn between ? and ?";
					Object[] data = {begin, end};
					return jdbcTemplate.query(sql, gameUserMapper, data);
				}
				
				public List<GameUserDto> selectList(String column, String keyword) {
					//검색 항목 검사
					switch(column) {
					case "game_user_id":
					case "game_user_job":
						break;
					default:
						throw new NoPermissionException("검색할 수 없는 항목");
					}
					
					String sql = "select "
										+ "game_user_no, game_user_id, "
										+ "game_user_job, game_user_level, game_user_money "
									+ "from game_user "
									+ "where instr(#1, ?) > 0 "
									+ "order by game_user_no desc";
					sql = sql.replace("#1", column);
					Object[] data = {keyword};
					return jdbcTemplate.query(sql, gameUserMapper, data);
				}
				public List<GameUserDto> selectListByPaging(
						String column, String keyword, int page, int size) {
					int begin = page * size - (size-1);
					int end = page * size;
					
					//검색 항목 검사
					switch(column) {
					case "game_user_id":
					case "game_user_job":
						break;
					default:
						throw new NoPermissionException("검색할 수 없는 항목");
					}
					
					String sql = "select * from ("
										+ "select rownum rn, TMP.* from ("
											+ "select "
												+ "game_user_no, game_user_id, "
												+ "game_user_job, game_user_level, game_user_money, "
											+ "from game_user "
											+ "where instr(#1, ?) > 0 "
											+ "order by game_user_no desc"
										+ ") TMP"
									+ ") where rn between ? and ?";
					sql = sql.replace("#1", column);
					Object[] data = {keyword, begin, end};
					return jdbcTemplate.query(sql, gameUserMapper, data);
				}
				//카운터 조회
				public int count() {
					String sql = "select count(*) from game_user";
					return jdbcTemplate.queryForObject(sql, int.class);
//					return jdbcTemplate.queryForObject(sql, Integer.class);
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
				        case "game_user_id":
				        case "game_user_job":
				            break;
				        default:
				            throw new NoPermissionException("검색할 수 없는 항목입니다.");
				    }

				    // SQL 쿼리 작성 및 실행
				    String sql = "select count(*) from game_user where instr(#1, ?) > 0";
				    sql = sql.replace("#1", column); // 컬럼명 동적 교체
				    Object[] params = {keyword};
				    return jdbcTemplate.queryForObject(sql, int.class, params);
				}
				
				public List<GameUserDto> selectListByPaging(PageVO pageVO) {
				    if (pageVO.isList()) {
				        return selectListByPaging(pageVO.getPage(), pageVO.getSize());
				    } else {
				        return selectListByPaging(pageVO.getColumn(), pageVO.getKeyword(), pageVO.getPage(), pageVO.getSize());
				    }
				}	
				
				
				
			private Map<String, String> columnExample=Map.of(
					"아이디","game_user_id",
					"직업","game_user_job");
			
			
			public GameUserDto selectOne(int gameUserNo) {
			String sql = "select * from game_user where game_user_no =? ";
			Object[] data = {gameUserNo};
			List<GameUserDto> list=jdbcTemplate.query(sql, gameUserMapper,data);
			return list.isEmpty() ? null : list.get(0);
	}
//
			public boolean GameUserLevel(int gameUserNo) {
				String sql="update game_user"
						+"set game_user_level=game_user_level+1 "
						+"where game_user_no=? ";
				Object[]data= {gameUserNo};
			return jdbcTemplate.update(sql,data)>0;
			}
		


}
	
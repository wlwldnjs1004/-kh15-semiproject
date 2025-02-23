package com.kh.spring09.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



//@Repository
//public class ItemDao {
//
//	
//	@Autowired
//	private ItemMapper itemMapper;
//	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//	   public void insert(ItemDto itemDto) {
//	        String sql = "insert into item("
//	        		+ "item_no,item_name,item_type,item_price,item_discount_rate,item_qty,item_early"    
//	                + ") values (item_seq.nextval, ?, ?, ?, ?, ?, ?)";
//	        Object[] data = {
//	        		itemDto.getItemName(),
//	        		itemDto.getItemType(),
//	        		itemDto.getItemPrice(),
//	        		itemDto.getItemDiscountRate(),
//	        		itemDto.getItemQty(),
//	        		itemDto.getItemEarly()
//	        };
//	        jdbcTemplate.update(sql, data);
//	    }
//	
//public boolean update( ItemDto  itemDto) { // 수정된 메소드 이름
//   String sql = "update  item "
//           + " set  item_name = ?,  item_type = ? , item_price = ? ,  item_discount_rate = ? , item_qty = ? , item_early = ? "
//           + " where  item_no = ? ";
//   Object[] data = {
//   		itemDto.getItemName(),
//   		itemDto.getItemType(),
//   		itemDto.getItemPrice(),
//   		itemDto.getItemDiscountRate(),
//   		itemDto.getItemQty(),
//   		itemDto.getItemEarly(),
//   		itemDto.getItemNo()
//   
//   };
//   int rows = jdbcTemplate.update(sql, data);
//   return rows > 0;
//}
//public boolean delete(int itemNo) {
//	String sql = "delete item where item_no = ?";
//	Object[] data= {itemNo};
//	int rows = jdbcTemplate.update(sql,data);
//	return rows> 0;
//	
//}
//public List<ItemDto>selectList(){
//	String sql="select * from item " ;
//	return jdbcTemplate.query(sql, itemMapper);
//}
//private Map<String, String> columnExample=Map.of(
//		"상품이름","item_name",
//		"상품종류","item_type");
//public List<ItemDto> selectList(String column, String keyword) {
//   String columnName = columnExample.get(column);
//   if (columnName == null) {
//       throw new RuntimeException("항목 오류: 유효하지 않은 검색 항목입니다.");
//   }
//
//   String sql = "select * from item where "
//              + "instr(" + columnName + ", ?) > 0 "
//              + "order by " + columnName + " asc, item_no asc";
// Object[] data = { keyword };
// return jdbcTemplate.query(sql, itemMapper, data);
//}
//public ItemDto selectOne(int itemNo) {
//	String sql = "select * from item where item_no = ? ";
//	Object[] data = {itemNo};
//	List<ItemDto> list = jdbcTemplate.query(sql,itemMapper, data);
//	return list.isEmpty() ? null:list.get(0);
//	
//}
//
//
//}

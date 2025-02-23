package com.kh.spring09.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kh.spring09.dto.PokemonDto;

//이 클래스는 pokemon 테이블을 조회(select)한 결과를 변환하는 클래스
//- 소모품이 아니기 때문에 싱글톤 방식을 쓰는 것이 효과적
//- 스프링에게 등록을 요청해서 관리 및 혜택을 받는다
//- @Component 라는 애노테이션으로 등록한다 (단위기능을 수행하는 도구)
@Component
public class PokemonMapper implements RowMapper<PokemonDto>{
	@Override
	public PokemonDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PokemonDto pokemonDto = new PokemonDto();
		pokemonDto.setPokemonNo(rs.getInt("pokemon_no"));
		pokemonDto.setPokemonName(rs.getString("pokemon_name"));
		pokemonDto.setPokemonType(rs.getString("pokemon_type"));
		return pokemonDto;
	}
}
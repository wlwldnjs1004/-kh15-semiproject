package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//등록은 왜 하는 걸까?
//-인스턴스를 직접 생성하지 않고 스프링에게 생성을 요청하는것
//- 특별한 옵션이 없다면 1개만 생성(싱글톤, singleton)
//-DTO는 여려 개 존재하며 필요할 때마다 만들어야 한다
//-둥록을 하지 않고 그때그때 new로 만들어서 사용
//@Setter@Getter@NoArgsConstructor@ToString

@Data//Method, automatic generation/Automatically addsmore tools
@NoArgsConstructor//Object Settings
@AllArgsConstructor//생성자를 만들어줌
public class PokemonDto {
 private int pokemonNo;
 private String pokemonName;
 private String pokemonType;
}

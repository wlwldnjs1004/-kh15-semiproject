package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
	private int menuNo;
	private String menuName;
	private String menuType;
	private int menuPrice;
	private String menuEvent;
}

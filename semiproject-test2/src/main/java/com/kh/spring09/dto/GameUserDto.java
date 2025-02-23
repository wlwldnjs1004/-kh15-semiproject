package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameUserDto {
	private int gameUserNo;
	private String gameUserId;
	private String gameUserJob;
	private int gameUserLevel;
	private int gameUserMoney;
}

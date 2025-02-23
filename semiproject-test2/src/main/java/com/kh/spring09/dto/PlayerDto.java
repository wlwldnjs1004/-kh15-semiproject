package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
	private int playerNo;
	private String playerName;
	private String playerEvent;
	private String playerType;
	private int playerGoldMedal, playerSilverMedal, playerBronzeMedal;
}
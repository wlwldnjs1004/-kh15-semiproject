package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {
	private int phoneNo;
	private String phoneName;
	private String phoneTelecom;
	private int phonePrice;
	private Integer phoneContract;//null 가능
}
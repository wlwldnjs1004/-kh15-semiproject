package com.kh.spring09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
	private int countryNo;
	private String countryName;
	private String countryCapital;
	private long countryPopulation;
	
}

package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("dust")
public class Dust {
	private String dust_loc;
	private String dust_grade;
	private String dust_date;
}

package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("shortWeather")
public class ShortWeather {
	
	private int sw_no;
	private String sw_rainper;
	private String sw_temp;
	private String sw_loc;
	private String sw_date;
	private String sw_time;
	
}

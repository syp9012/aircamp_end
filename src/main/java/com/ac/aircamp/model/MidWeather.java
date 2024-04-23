package com.ac.aircamp.model;


import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("midWeather")
public class MidWeather {
	
	private String mw_mintep;
	private String mw_maxtep;	
	private String mw_rainper;
	private String mw_loc;
	private Date mw_date;



}

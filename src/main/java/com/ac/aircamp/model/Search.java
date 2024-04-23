package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("search")
public class Search {

	
	private String addr1;
	private String addr2;
	private String keyword;
	private int limit;
	private int page = 1;
	private int startRow;
	
	
}

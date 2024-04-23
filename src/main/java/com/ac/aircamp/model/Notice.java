package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("notice")
public class Notice {
	
	int no_no;
	String no_subject;
	String no_content;
	int no_readcount;
	String no_date;
	int no_level;
	int no_ref;
	int no_step;
	int u_no;
	
}

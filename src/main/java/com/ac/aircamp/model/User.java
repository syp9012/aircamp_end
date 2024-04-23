package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("user")
public class User {
	private String u_no;
	private String u_id;
	private String u_pwd;
	private String u_nickname;
	private String u_birth;
	private String u_zipcode;
	private String u_addr1;
	private String u_addr2;
	private String u_email;
	private String u_domain;
	private String u_phone;
	private String u_profile;
	private String u_withdraw;
	private String u_social;
}



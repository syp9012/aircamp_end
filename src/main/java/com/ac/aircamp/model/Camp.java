package com.ac.aircamp.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("camp")
public class Camp {
	
	private int camp_no;
    private String camp_name;
    private String camp_intro;
    private String camp_desc;
    private String camp_scale;
    private String camp_insurance;
    private String camp_owner;
    private String camp_dayoff_start;
    private String camp_dayoff_end;
    private String camp_feature;
    private String camp_type;
    private String camp_nature_type;
    private String camp_do_name;
    private String camp_city_name;
    private String camp_zipcode;
    private String camp_addr1;
    private String camp_addr2;
    private String camp_locx;
    private String camp_locy;
    private String camp_tel;
    private String camp_url;
    private String camp_reserve_url;
    private String camp_reserve_type;
    private String camp_normal_no;
    private String camp_car_no;
    private String camp_glamp_no;
    private String camp_carav_no;
    private String camp_glamp_inner_fclty;
    private String camp_carav_inner_fclty;
    private String camp_open_season;
    private String camp_open_date;
    private String camp_toilet_no;
    private String camp_shower_no;
    private String camp_sink_no;
    private String camp_add_fclty;
    private String camp_able_fclty;
    private String camp_equip_rent;
    private String camp_able_animal;
    private String camp_image;
}

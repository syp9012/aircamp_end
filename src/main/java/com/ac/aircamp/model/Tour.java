package com.ac.aircamp.model;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("tour")
public class Tour {

	private String tour_id;
	private String tour_name;
	private String tour_url;
	private String tour_tel;
	private String tour_city;
	private String tour_addr1;
	private String tour_addr2;
	private String tour_zipcode;
	private String tour_locx;
	private String tour_locy;
	private String tour_desc; 
	private String tour_babycarry;
	private String tour_animal_able;
	private String tour_age_able;
	private String tour_heritage1;
	private String tour_heritage2;
	private String tour_heritage3;
	private String tour_infocenter;
	private String tour_open_date;
	private String tour_parking;
	private String tour_dayoff;
	private String tour_usetime;
	private String tour_images;
	private String tour_mainimage;
	private String tour_subimage1;
	private String tour_subimage2;
	private String tour_subimage3;
	
	private List<String> tour_subimage;
}

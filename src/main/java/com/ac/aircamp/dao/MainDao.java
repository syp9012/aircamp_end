package com.ac.aircamp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ac.aircamp.model.Camp;
import com.ac.aircamp.model.Tour;

@Mapper
public interface MainDao {

	List<Tour> getTourList(String loc);

	List<Camp> getCampList(String loc);

	String getRecommendLoc();

}

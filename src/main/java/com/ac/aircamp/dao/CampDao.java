package com.ac.aircamp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ac.aircamp.model.Camp;
import com.ac.aircamp.model.Search;

@Mapper
public interface CampDao {

	public List<Camp> searchCampList(Search search);
	public int searchCampCount(Search search);
	public Camp getCampOne(int camp_no);
	
	
}

package com.ac.aircamp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ac.aircamp.model.Search;
import com.ac.aircamp.model.Tour;

@Mapper
public interface TourDao {

	List<Tour> searchTourList(Search search);

	int searchTourCount(Search search);

	Tour getTourOne(int tour_id);

	
}

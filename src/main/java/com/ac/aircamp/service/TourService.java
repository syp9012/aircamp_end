package com.ac.aircamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.TourDao;
import com.ac.aircamp.model.Search;
import com.ac.aircamp.model.Tour;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourService {

	private final TourDao tourDao;
	
	public List<Tour> searchTourList(Search search){
		return tourDao.searchTourList(search);
	}
	
	public int searchTourCount(Search search) {
		return tourDao.searchTourCount(search);
	}
	
	public Tour getTourOne(int tour_id) {
		return tourDao.getTourOne(tour_id);
	}
}

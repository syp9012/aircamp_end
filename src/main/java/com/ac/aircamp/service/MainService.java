package com.ac.aircamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.MainDao;
import com.ac.aircamp.model.Camp;
import com.ac.aircamp.model.Tour;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {

	private final MainDao mainDao;
	
	public List<Tour> getTourList(String loc) {
		return mainDao.getTourList(loc);
	}

	public List<Camp> getCampList(String loc) {
		return mainDao.getCampList(loc);
	}
	
	public String getRecommendLoc() {
		return mainDao.getRecommendLoc();
	}
	
}

package com.ac.aircamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.CampDao;
import com.ac.aircamp.model.Camp;
import com.ac.aircamp.model.Search;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CampService {
	
	private final CampDao campDao;
	
	public List<Camp> searchCampList(Search search){
		return campDao.searchCampList(search);
	}
	
	public int searchCampCount(Search search) {
		return campDao.searchCampCount(search);
	}

	public Camp getCampOne(int camp_no) {
		return campDao.getCampOne(camp_no);
	}
}

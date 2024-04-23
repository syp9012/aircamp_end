package com.ac.aircamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.PublicApiDao;
import com.ac.aircamp.model.CampApi;
import com.ac.aircamp.model.Dust;
import com.ac.aircamp.model.MidWeather;
import com.ac.aircamp.model.ShortWeather;
import com.ac.aircamp.model.Tour;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PublicApiService{

	private final PublicApiDao publicApiDao;
	
	//중기
	public void mwInsert(MidWeather midTemp) {
		publicApiDao.mwInsert(midTemp);
	}
	
	public void mwUpdate(MidWeather midRainper) {
		publicApiDao.mwUpdate(midRainper);
	}

	public void mwDelete() {
		publicApiDao.mwDelete();		
	}
	//단기
	public void swInsert(ShortWeather shortWeater) throws Exception {
		publicApiDao.swInsert(shortWeater);
	}

	public void swUpdate(ShortWeather shortWeater) throws Exception {
		publicApiDao.swUpdate(shortWeater);
	}

	public void swDelete() {
		publicApiDao.swDelete();
	}
	//미세
	public int dustDelete() {
		return publicApiDao.dustDelete();
	}
	
	public int dustInsert(List<Dust> list) {
		return publicApiDao.dustInsert(list);
	}
	//관광지
	public void tourInsert(Tour tour) {
		publicApiDao.tourInsert(tour);
	}
	//캠핑장
	public int campInsert(List<CampApi> list) {
		return publicApiDao.campInsert(list);
	}
}

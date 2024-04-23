package com.ac.aircamp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ac.aircamp.model.CampApi;
import com.ac.aircamp.model.Dust;
import com.ac.aircamp.model.MidWeather;
import com.ac.aircamp.model.ShortWeather;
import com.ac.aircamp.model.Tour;


@Mapper
public interface PublicApiDao {

	//중기
	int mwInsert(MidWeather midTemp);
	int mwUpdate(MidWeather midRainper);
	void mwDelete();
	
	//단기
	void swInsert(ShortWeather shortWeather ) throws Exception;
	void swUpdate(ShortWeather shortWeather) throws Exception;
	void swDelete();

	//미세
	int dustDelete();
	int dustInsert(List<Dust> list);
	
	//관광지
	void tourInsert(Tour tour);
	
	//캠핑장
	int campInsert(List<CampApi> list);
}

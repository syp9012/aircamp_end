package com.ac.aircamp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.Alias;

import com.ac.aircamp.model.Dust;
import com.ac.aircamp.model.MidWeather;
import com.ac.aircamp.model.ShortWeather;

@Mapper
public interface WeatherDao {

	public List<ShortWeather> getShortWeatherList(String loc);
	
	public List<Dust> getDustList(String loc);
	
	public List<MidWeather> getMidWeatherList(String loc);
}

package com.ac.aircamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ac.aircamp.dao.WeatherDao;
import com.ac.aircamp.model.Dust;
import com.ac.aircamp.model.MidWeather;
import com.ac.aircamp.model.ShortWeather;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {
	
	private final WeatherDao weatherDao;
	
	public List<ShortWeather> getShortWeatherList(String loc) {
		return weatherDao.getShortWeatherList(loc);
	}
	public List<Dust> getDustList(String loc) {
		return weatherDao.getDustList(loc);
	}
	public List<MidWeather> getMidWeatherList(String loc) {
		return weatherDao.getMidWeatherList(loc);
	}
	
}

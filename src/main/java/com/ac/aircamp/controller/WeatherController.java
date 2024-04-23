package com.ac.aircamp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ac.aircamp.model.Dust;
import com.ac.aircamp.model.MidWeather;
import com.ac.aircamp.model.ShortWeather;
import com.ac.aircamp.service.WeatherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WeatherController {
	
	private final WeatherService weatherService;
	
	@RequestMapping("/weather")
	public String weather(Model model, @RequestParam(value = "loc", defaultValue="서울") String loc) {
        List<ShortWeather> sw = weatherService.getShortWeatherList(loc);
        List<Dust> dust = weatherService.getDustList(loc);
        List<MidWeather> mw = weatherService.getMidWeatherList(loc);
        
        model.addAttribute("sw", sw);        
        model.addAttribute("dust", dust);
        model.addAttribute("mw", mw);
        model.addAttribute("loc", loc);
	    
	    return "weather/list";
	}
	
	
	
	

}

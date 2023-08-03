package com.study.personal.Controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.personal.Service.weatherService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WeatherController {
	
	@Value("${weather_api_key}")
	private String weather_api_key;
	
	@Autowired
	private weatherService weatherService;
	
	@GetMapping("/weather")
	public String restApiGetWeather(Model model) throws IOException, JSONException {
		
        ArrayList<ArrayList<String>> result = weatherService.getWeatherInfo(weather_api_key);
        
        ArrayList<String> resultString = weatherService.mapToString(result);
		
        model.addAttribute("resultString", resultString);
        
        return "weather";
        
    }
	
	
}



package com.study.personal.Service;

import java.io.IOException;
import java.util.ArrayList;

public interface weatherService {
	
	ArrayList<ArrayList<String>> getWeatherInfo(String weather_api_key) throws IOException;
	
	ArrayList<String> mapToString(ArrayList<ArrayList<String>> result);
	
}

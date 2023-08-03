package com.study.personal.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class weatherServiceImpl implements weatherService{

	@Override
	public ArrayList<ArrayList<String>> getWeatherInfo(String weather_api_key) throws IOException {
		LocalDateTime now = LocalDateTime.now();
		String YearMonthDay = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String HourMinute = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("HH"))) - 1 + "00";
		if(HourMinute.length() == 3) HourMinute = "0" + HourMinute;
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + weather_api_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("50", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(YearMonthDay, "UTF-8")); /*‘21년 6월 28일발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(HourMinute, "UTF-8")); /*05시 발표*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
                
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        return parseToJson(sb.toString());
		
	}

	private ArrayList<ArrayList<String>> parseToJson(String stringObj) {
		
		JSONObject jsonObject = new JSONObject(stringObj);
		
		JSONObject response = jsonObject.getJSONObject("response");
		JSONObject header = response.getJSONObject("header");
		JSONObject body = response.getJSONObject("body");
		JSONObject items = body.getJSONObject("items");
		JSONArray item = items.getJSONArray("item");
		
		Map<String, Map<String, String>> returnMap = new HashMap<>(); 
		Map<String, String> skyMap = new LinkedHashMap<>(); // 하늘상태 : 맑음(1), 구름많음(3), 흐림(4)
		Map<String, String> temperMap = new LinkedHashMap<>(); // 온도
		Map<String, String> moistureMap = new LinkedHashMap<>(); // 습도
		Map<String, String> weatherMap = new LinkedHashMap<>(); // 강수형태 : 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7) 
		String date, time, val;
		
		/*
		for(int i=0; i<item.length(); i++) {
			switch (item.getJSONObject(i).getString("category")) {
				case "SKY":
					date = item.getJSONObject(i).getString("fcstDate");
					time = item.getJSONObject(i).getString("fcstTime");
					val = item.getJSONObject(i).getString("fcstValue");
					skyMap.put(date + time, val);
					break;
					
					
				case "T1H":
					date = item.getJSONObject(i).getString("fcstDate");
					time = item.getJSONObject(i).getString("fcstTime");
					val = item.getJSONObject(i).getString("fcstValue");
					temperMap.put(date + time, val);
					break;
					
				case "REH":
					date = item.getJSONObject(i).getString("fcstDate");
					time = item.getJSONObject(i).getString("fcstTime");
					val = item.getJSONObject(i).getString("fcstValue");
					moistureMap.put(date + time, val);
					break;

				case "PTY":
					date = item.getJSONObject(i).getString("fcstDate");
					time = item.getJSONObject(i).getString("fcstTime");
					val = item.getJSONObject(i).getString("fcstValue");
					weatherMap.put(date + time, val);
					break;
			}
			
			
		}
		
		returnMap.put("sky", skyMap);
		returnMap.put("temper", temperMap);
		returnMap.put("moisture", moistureMap);
		returnMap.put("weather", weatherMap);
		*/
		
		ArrayList<ArrayList<String>> testList = new ArrayList<>(); // 날짜, 온도, 습도, 하늘상태
		
		for(int i=0; i<item.length(); i++) {
			String category = item.getJSONObject(i).getString("category");
			if("SKY".equals(category)) {
				ArrayList<String> arrayList = new ArrayList<>();
				String fcstDate = item.getJSONObject(i).getString("fcstDate");
				String fcstTime = item.getJSONObject(i).getString("fcstTime");
				String fcstValue = item.getJSONObject(i).getString("fcstValue");
				arrayList.add(fcstDate);
				arrayList.add(fcstTime);
				arrayList.add(category);
				arrayList.add(fcstValue);
				
				testList.add(arrayList);
			}
		}
	
		System.out.println( testList.toString());
		
		
		return testList;
	}

	@Override
	public ArrayList<String> mapToString(ArrayList<ArrayList<String>> result) {
		
		ArrayList<String> resultString = new ArrayList<>();
		
		for (ArrayList<String> arrayList : result) {
			String a = arrayList.get(0).substring(0, 4) + "년 " +  arrayList.get(0).substring(4, 6) + "월 " + arrayList.get(0).substring(6) + "일" + arrayList.get(1).substring(0, 2) + "시 ";
			String b = "";
			if("SKY".equals(arrayList.get(2))) {
				switch (arrayList.get(3)) {
				case "1":
					b += "하늘 : 맑음";
					break;
				case "3":
					b += "하늘 : 구름많음";
					break;
				case "4":
					b += "하늘 : 흐림";
					break;
				}
			}
			
			resultString.add(a+b);
		}
		
		return resultString;
	}
	
}

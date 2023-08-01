package com.study.personal.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WeatherController {
	
	@Value("${weather_api_key}")
	private String weather_api_key;
	
	@GetMapping("/weather")
	@ResponseBody
	public void restApiGetWeather() throws IOException, JSONException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + weather_api_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20230801", "UTF-8")); /*‘21년 6월 28일발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1700", "UTF-8")); /*05시 발표*/
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
        
        parseToJson(sb.toString());
        
    }

	private void parseToJson(String stringObj) {
		
		JSONObject jsonObject = new JSONObject(stringObj);
		System.out.println(jsonObject);
		
		JSONObject response = jsonObject.getJSONObject("response");
		System.out.println(response);
		
		JSONObject header = response.getJSONObject("header");
		System.out.println(header);
		
		JSONObject body = response.getJSONObject("body");
		System.out.println(body);
		
		JSONObject items = body.getJSONObject("items");
		System.out.println(items);
		
		JSONArray item = items.getJSONArray("item");
		System.out.println(item);
		
		HashMap<String, String> testMap = new HashMap<>();
		
		for(int i=0; i<item.length(); i++) {
			if("SKY".equals(item.getJSONObject(i).getString("category"))){
				String date = item.getJSONObject(i).getString("fcstDate");
				String time = item.getJSONObject(i).getString("fcstTime");
				String val = item.getJSONObject(i).getString("fcstValue");
				testMap.put(date + time, val);
			}
		}
		
		System.out.println(testMap.toString());
		
	}
		
	
}



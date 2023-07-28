package com.study.personal.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WeatherController {
	
	@Value("${weather_api_key}")
	private String weather_api_key;
	
	@GetMapping("/weather")
	@ResponseBody
	public String restApiGetWeather() throws IOException, JSONException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + weather_api_key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20230728", "UTF-8")); /*‘21년 6월 28일발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8")); /*05시 발표*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        
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
        String result = sb.toString();
        
        // response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");

        // body 로 부터 items 찾기
        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");
        
        // items로 부터 itemlist 를 받기 
        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");
        
        String weather = "";
        String tmperature = "";
        for(int i=0;i<jsonArray.length();i++){
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");
            
            if(category.equals("SKY")){
                weather = "현재 날씨는 ";
                if(fcstValue.equals("1")) {
                    weather += "맑은 상태로";
                }else if(fcstValue.equals("2")) {
                    weather += "비가 오는 상태로 ";
                }else if(fcstValue.equals("3")) {
                    weather += "구름이 많은 상태로 ";
                }else if(fcstValue.equals("4")) {
                    weather += "흐린 상태로 ";
                }
            }

            if(category.equals("T3H") || category.equals("T1H")){
                tmperature = "기온은 "+fcstValue+"℃ 입니다.";
            }
            
        }
        
        return weather + tmperature;
    }
	
	
}



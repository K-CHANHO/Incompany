package com.study.personal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.study.personal.DTO.memberDTO;
import com.study.personal.Entity.Member;
import com.study.personal.Service.memberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private memberService memberService;
	
	@GetMapping("/member_join")
	public void goToLoginJoin() {}
	
	@PostMapping("/member_join")
	public String member_join(memberDTO memberDTO) {
		
		System.out.println(memberDTO);
		memberService.join(memberDTO);
		
		return "index";
	}
	
	@PostMapping("/login")
	public String login(memberDTO memberDTO, Model model) {
		
		Member member = memberService.login(memberDTO);
		if(member != null) {
			model.addAttribute("userid", memberDTO.getUserid());
			return "main";
		} else {
			return "index";
		}
		
	}
	
	/**
	 * 카카오 로그인
	 */
	
	@Value("${rest_api_key}")
	private String rest_api_key;
	
	@Value("${redirect_uri}")
	private String redirect_uri;

	@GetMapping("/kakaoLogin")
	public String kakaoLogin() {
		
		log.info(rest_api_key);
		log.info(redirect_uri);
		
		String url = "https://kauth.kakao.com/oauth/authorize?"
						+ "client_id=" + rest_api_key
						+ "&redirect_uri=" + redirect_uri
						+ "&response_type=code";
		
		return "redirect:" + url;
	}
	
	@GetMapping("/oauth/kakao")
	public String getCode(@RequestParam String code, Model model) {
		
		RestTemplate rT = new RestTemplate();
		
		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 바디 설정
		MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
		bodyParams.add("grant_type", "authorization_code");
		bodyParams.add("client_id", rest_api_key);
		bodyParams.add("redirect_uri", redirect_uri);
		bodyParams.add("code", code);
		
		// Header + Body
		HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(bodyParams, headers);
		
		// 요청하고 Token 받기
		ResponseEntity<String> response = rT.exchange("https://kauth.kakao.com/oauth/token",
														HttpMethod.POST,
														accessTokenRequest,
														String.class);
		
		System.out.println(response.getBody());
		
		JsonElement element = JsonParser.parseString(response.getBody());
		
		String accessToken = element.getAsJsonObject().get("access_token").getAsString();
		
		
		
		// userinfo 가져오기
		RestTemplate rT2 = new RestTemplate();
		
		// 헤더 설정
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+ accessToken);
		headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		HttpEntity<MultiValueMap<String, String>> accessTokenRequest2 = new HttpEntity<>(headers2);
		
		ResponseEntity<String> response2 = rT.exchange("https://kapi.kakao.com/v2/user/me",
														HttpMethod.POST,
														accessTokenRequest2,
														String.class);
		
		JsonElement element2 = JsonParser.parseString(response2.getBody());
		String userid = element2.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
		
		model.addAttribute("userid", userid);
		
		return "main";
	}

}

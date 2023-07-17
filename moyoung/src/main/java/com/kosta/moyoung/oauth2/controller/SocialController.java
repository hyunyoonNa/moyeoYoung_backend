package com.kosta.moyoung.oauth2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
public class SocialController {
	
	@PostMapping("/oauth2/code/kakao")
	public String kakaoLog(String code) {
		System.out.println(code);
		return code;
	}
}

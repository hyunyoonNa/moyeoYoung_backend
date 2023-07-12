package com.kosta.moyoung.openchat.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TenorController {
	
	@Value("${tenor-key}")
	private String tenor_key;
	
	@Value("${tenor-client-key")
	private String tenor_client_key;
	
    @GetMapping("/search-gifs")
    public String searchGifs(@RequestParam String q) {
    	
        String url = "https://tenor.googleapis.com/v2/search?q=" + q + "&key="+ tenor_key
        		+"&client_key="+ tenor_client_key + "&limit=24";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
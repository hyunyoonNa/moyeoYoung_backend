package com.kosta.moyoung.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	@Value("${api.base.furl}")
	private String apiUrl;
	
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버 응답시 json 을 자바스크립트에서 처리할 수 있음
        config.addAllowedOriginPattern(apiUrl); // 모든 ip 에 응답 허용
        config.addAllowedHeader("*"); // 모든 header 응답 허용
        config.addExposedHeader("*");
        config.addAllowedMethod("*"); // 모든 요청 메소드 응답 허용
        source.registerCorsConfiguration("/auth/**", config);
        source.registerCorsConfiguration("/member/**", config);
        source.registerCorsConfiguration("/login/**", config);
        source.registerCorsConfiguration("/room/**", config);
        source.registerCorsConfiguration("/note/**", config);
        source.registerCorsConfiguration("/rooms/**", config);
        source.registerCorsConfiguration("/room/**", config);
        source.registerCorsConfiguration("/feed/**", config);
        source.registerCorsConfiguration("/ws/**", config);
        source.registerCorsConfiguration("/todos/**", config);
        return new CorsFilter(source);
    }
}
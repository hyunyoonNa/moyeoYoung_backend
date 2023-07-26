package com.kosta.moyoung.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.kosta.moyoung.member.dto.TokenDto;
import com.kosta.moyoung.security.jwt.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
		
	@Value("${api.base.furl}")
	private String apiUrl;
	
	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		SecurityContextHolder.getContext().setAuthentication(authentication); //사용자정보 Security Context에 저장
		response.setStatus(HttpServletResponse.SC_OK);
		String memberId = authentication.getName();
		log.info(memberId);
		TokenDto accessToken = tokenProvider.generateTokenDto(authentication);
		response.setHeader("Access-Control-Allow-Origin", apiUrl);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"userid\":\""+memberId+"\",\"accessToken\":\""+accessToken+"\"}");
		response.getWriter().flush();	    
	}
	
}

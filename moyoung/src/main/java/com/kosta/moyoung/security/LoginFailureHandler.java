package com.kosta.moyoung.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Value("${api.base.furl}")
	private String apiUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
        System.out.println("exception : " + exception);
		response.setHeader("Access-Control-Allow-Origin", apiUrl);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("text");
		response.setCharacterEncoding("utf-8");
        response.getWriter().write("login failed");
		response.getWriter().flush();
	}

}

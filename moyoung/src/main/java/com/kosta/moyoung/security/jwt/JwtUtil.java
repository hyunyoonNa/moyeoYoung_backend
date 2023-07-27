package com.kosta.moyoung.security.jwt;

import java.util.Base64;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class JwtUtil {
	 // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }
        String memberId = authentication.getName();
        if (memberId.equals("anonymousUser")) {
            return null;
        }
        return Long.parseLong(authentication.getName());
    }
    //수정할꺼
    
	public static Long getCurrentMemberIdFromToken(String accessToken) {
		  try {
		        if (accessToken == null || accessToken.isEmpty()) {
		            throw new IllegalArgumentException("AccessToken is empty or null.");
		        }

		        // JWT 토큰을 점(.)으로 분리하여 각 부분 추출
		        String[] parts = accessToken.split("\\.");

		        if (parts.length < 2) {
		            throw new IllegalArgumentException("Invalid AccessToken format.");
		        }

		        String payload = parts[1]; // 페이로드 부분은 두 번째 요소

		        // Base64 디코딩 후 JSON 문자열 추출
		        String decodedPayload = new String(Base64.getDecoder().decode(payload));

		        // JSON 문자열을 파싱하여 사용자 정보 얻기
		        ObjectMapper objectMapper = new ObjectMapper();
		        Map<String, Object> payloadMap = objectMapper.readValue(decodedPayload, new TypeReference<Map<String, Object>>() {});

		        // 사용자 ID 추출
		        if (!payloadMap.containsKey("sub")) {
		            throw new IllegalArgumentException("Invalid AccessToken payload: 'sub' key not found.");
		        }

		        Long memberId = Long.parseLong(payloadMap.get("sub").toString());
		        return memberId;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return null; // 토큰 파싱 실패 시 null 반환
		    }
	}
}

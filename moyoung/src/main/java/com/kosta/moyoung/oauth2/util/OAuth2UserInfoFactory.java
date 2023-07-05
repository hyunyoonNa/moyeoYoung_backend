package com.kosta.moyoung.oauth2.util;

import java.util.Map;

import com.kosta.moyoung.advice.DefaultAssert;
import com.kosta.moyoung.member.entity.Provider;
import com.kosta.moyoung.oauth2.company.Kakao;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		
//		if (registrationId.equalsIgnoreCase(Provide.kakao.toString())) {
//			return new Google(attributes);
//		} else if (registrationId.equalsIgnoreCase(Provider.naver.toString())) {
//			return new Naver(attributes);
//		} else if (registrationId.equalsIgnoreCase(Provider.kakao.toString())) {
		if (registrationId.equalsIgnoreCase(Provider.kakao.toString())) {
			return new Kakao(attributes);
		} else {
			DefaultAssert.isAuthentication("해당 oauth2 기능은 지원하지 않습니다.");
		}
		return null;
	}
}
package com.kosta.moyoung.oauth2.company;

import java.util.Map;

import com.kosta.moyoung.member.entity.Provider;
import com.kosta.moyoung.oauth2.util.OAuth2UserInfo;
import com.kosta.moyoung.oauth2.util.OAuth2UserInfoFactory;

public class Kakao extends OAuth2UserInfo {

	  public Kakao(Map<String, Object> attributes) {
	        super(attributes);
	    }

	    @Override
	    public String getId() {
	        return attributes.get("id").toString();
	    }

	    @Override
	    public String getName() {
	        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

	        if (properties == null) {
	            return null;
	        }

	        return (String) properties.get("nickname");
	    }

	    @Override
	    public String getEmail() {
	        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");
	        if (properties == null) {
	            return null;
	        }
	        return (String) properties.get("email");
	    }

	    @Override
	    public String getImageUrl() {
	        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

	        if (properties == null) {
	            return null;
	        }

	        return (String) properties.get("thumbnail_image");
	    }

	    @Override
	    public String getProvider(){
	        return Provider.kakao.toString();
	    }
}
package com.kosta.moyoung.oauth2.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.kosta.moyoung.member.entity.Authority;

import lombok.Getter;

@Getter
public class CustomOAuth2User  extends DefaultOAuth2User {
	
	 private String email;
	 private Authority authority;
	 
	 public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String email, Authority authority) {
		 super(authorities, attributes, nameAttributeKey);
		 this.email = email;
		 this.authority = authority;
		 
	 }
}

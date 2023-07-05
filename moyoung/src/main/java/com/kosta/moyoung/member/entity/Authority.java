package com.kosta.moyoung.member.entity;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

//spring Security에는 user의 role이 필요하므로 enum타입의 role추가.
@Getter
public enum Authority {
	ROLE_USER, ROLE_ADMIN;
	
	 @Autowired
	 private String value;
}

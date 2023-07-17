package com.kosta.moyoung.member.util;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.kosta.moyoung.member.entity.Member;

public class Adapter extends User {

	private Member member;
	
	public Adapter(Member member) {
		super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
			this.getAuthorities();
	}
	
}

package com.kosta.moyoung.member.service;

import java.util.Collections;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;

@Service
public class CustomMemberService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email).map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	// DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(Member member) {
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

		return new User(String.valueOf(member.getMemeberId()), member.getPassword(), Collections.singleton(grantedAuthority));
	}
}

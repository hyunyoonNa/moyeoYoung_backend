package com.kosta.moyoung.member.service;

import org.springframework.stereotype.Service;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	 public MemberResponseDto findMemberInfoById(Long memberId) {
	        return memberRepository.findById(memberId)
	                .map(MemberResponseDto::of)
	                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	    }

	    public MemberResponseDto findMemberInfoByEmail(String email) {
	        return memberRepository.findByEmail(email)
	                .map(MemberResponseDto::of)
	                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
	    }
	    
	    
	
}

package com.kosta.moyoung.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	
	 private final MemberService memberService;
	 
	 	@GetMapping("/mypage")
	    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
	        return ResponseEntity.ok(memberService.findMemberInfoById(JwtUtil.getCurrentMemberId()));
	    }

	    @GetMapping("/{email}")
	    public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
	        return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
	    }
	    
	    
}

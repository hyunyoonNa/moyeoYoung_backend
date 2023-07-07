package com.kosta.moyoung.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.member.dto.MemberRequestDto;
import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.dto.TokenDto;
import com.kosta.moyoung.member.dto.TokenRequestDto;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final MemberRepository memberRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
    
    @GetMapping("/checkedemail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email){
    	boolean emailCheck = memberRepository.existsByEmail(email);
    	System.out.println("이메일 중복체크 : " +email);
    	return ResponseEntity.ok(emailCheck);
    }
    @GetMapping("/checkednick")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname){
    	boolean nicknameCheck = memberRepository.existsByNickname(nickname);
    	System.out.println("닉네임 중복체크 : " +nickname);
    	return ResponseEntity.ok(nicknameCheck);
    }

    
}
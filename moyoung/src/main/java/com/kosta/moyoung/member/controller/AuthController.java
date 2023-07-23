package com.kosta.moyoung.member.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.member.dto.MemberRequestDto;
import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.dto.PasswordRequestDto;
import com.kosta.moyoung.member.dto.TokenDto;
import com.kosta.moyoung.member.dto.TokenRequestDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.mail.RegisterMail;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.AuthService;
import com.kosta.moyoung.member.util.UserPrincipal;
import com.kosta.moyoung.security.jwt.JwtFilter;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final MemberRepository memberRepository;
    private final AuthService authService;
    
 // 회원가입 메일 서비스
 	private final RegisterMail registerMail;
 	
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
    	  if (emailCheck) {
    	        // 이메일이 존재하는 경우에만 인증 절차를 수행합니다.
    	        // 인증 절차를 수행하는 코드 작성
    	        return ResponseEntity.ok(true);
    	    } else {
    	        return ResponseEntity.ok(false);
    	    }
    }
    
    @GetMapping("/checkednick")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname){
    	boolean nicknameCheck = memberRepository.existsByNickname(nickname);
    	System.out.println("닉네임 중복체크 : " +nickname);
    	return ResponseEntity.ok(nicknameCheck);
    }
    
    
 // 이메일 인증
    @PostMapping("/mailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam("email") String email) throws Exception {
    	System.out.println(email);
       String code = registerMail.sendSimpleMessage(email);
       System.out.println(email);
       System.out.println("인증코드 : " + code);
       return code;
    }
   
    
}
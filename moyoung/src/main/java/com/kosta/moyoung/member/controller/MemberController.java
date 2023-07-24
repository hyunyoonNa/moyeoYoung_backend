package com.kosta.moyoung.member.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.dto.MemberRequestDto;
import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.dto.PasswordRequestDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.member.service.MemberServiceImpl;
import com.kosta.moyoung.member.util.UserPrincipal;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@GetMapping("/profile/{nickname}")
	public ResponseEntity<MemberResponseDto> memberProfile(@PathVariable String nickname) {
		try {
			MemberResponseDto memberDto = memberService.findMemberInfoByNickname(nickname);
			return new ResponseEntity<MemberResponseDto>(memberDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<MemberResponseDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/mypage")
	public ResponseEntity<MemberResponseDto> findMemberInfoById() {
		try {
			return ResponseEntity.ok(memberService.findMemberInfoById(JwtUtil.getCurrentMemberId()));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{email}")
	public ResponseEntity<MemberResponseDto> findMemberInfoByEmail(@PathVariable String email) {
		System.out.println(email);
		try {
			return ResponseEntity.ok(memberService.findMemberInfoByEmail(email));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}


	@PostMapping("/update/{memberId}")
	public ResponseEntity<String> updateMemberProfile(@PathVariable Long memberId,
			@ModelAttribute MemberRequestDto memberRequestDto,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				String dir = "C:/resources/upload/";
				String imgName = file.getOriginalFilename();
				System.out.println(imgName);
				File dfile = new File(dir + imgName);
				file.transferTo(dfile);
			}
			memberService.updateMember(memberId, memberRequestDto, file);
			return new ResponseEntity<String>("수정완료", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	// 비밀번호변경
	@PostMapping("/passwdUpdate")
	@ResponseBody
	public ResponseEntity<String> updatePassword(@RequestParam String email,
			@RequestBody PasswordRequestDto passwordRequestDto) {
		System.out.println(email);
		try {
			memberService.updatePassword(email, passwordRequestDto);
			return new ResponseEntity<String>("수정완료", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	// 회원탈퇴
	@DeleteMapping("/delete/{memberId}")
	public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
		try {
			memberService.deleteMember(memberId);
			System.out.println(memberId);
			return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생하였습니다.");
		}
	}

}

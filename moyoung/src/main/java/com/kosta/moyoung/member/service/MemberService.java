package com.kosta.moyoung.member.service;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.dto.MemberRequestDto;
import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.dto.PasswordRequestDto;
import com.kosta.moyoung.member.entity.Member;

public interface MemberService {

	// 회원아이디로조회
	MemberResponseDto findMemberInfoById(Long memberId) throws Exception;

	// 회원 이메일로 조회
	MemberResponseDto findMemberInfoByEmail(String email) throws Exception;
	
	// 회원 수정
	void updateMember(Long memberId, MemberRequestDto memberRequestDto, MultipartFile fileName) throws Exception;
	
	//비밀번호 재설정
	void updatePassword(String email, PasswordRequestDto passwordRequestDto) throws Exception;
	
	// 회원 탈퇴
	void delete(Long memberId) throws Exception;
	
	Member findMember(Long memberId)throws Exception;
	
	
}

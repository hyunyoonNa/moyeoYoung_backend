package com.kosta.moyoung.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.dto.MemberRequestDto;
import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.dto.PasswordRequestDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.dto.RoomDTO;

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
	void deleteMember(Long memberId) throws Exception;
	
	//프로필 조회
	MemberResponseDto findMemberInfoByNickname(String nickname) throws Exception;
	
	Member findMember(Long memberId)throws Exception;
	
	//북마크한 방 목록
	List<RoomDTO> roomListWithBookmark(Long memberId) throws Exception;
	
	//개설한 방 목록
	List<RoomDTO> madeRoomList(Long memberId) throws Exception;
	
	//가입한 방 목록
	Map<String,List<RoomDTO>> joinRoomList(Long memberId) throws Exception;
	
}

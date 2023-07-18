package com.kosta.moyoung.member.service;

import java.io.File;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.dto.MemberRequestDto;
import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.dto.PasswordRequestDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

//	private final MemberService memberService;
	private final MemberRepository memberRepository;
//	private final ModelMapper modelMapper;

	@Transactional
	@Override
	public MemberResponseDto findMemberInfoById(Long memberId) {
		System.out.println(JwtUtil.getCurrentMemberId());
		return memberRepository.findById(memberId).map(MemberResponseDto::of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}

	@Transactional
	@Override
	public MemberResponseDto findMemberInfoByEmail(String email) {
		return memberRepository.findByEmail(email).map(MemberResponseDto::of)
				.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
	}

	@Override
	public void updateMember(Long memberId, MemberRequestDto memberRequestDto, MultipartFile fileName) throws Exception {
		Optional<Member> omember = memberRepository.findById(memberId);
		Member member = omember.get();
		
		member.setFileName(memberRequestDto.getFileName());
		member.setNickname(memberRequestDto.getNickname());
		member.setProfileContent(memberRequestDto.getProfileContent());
		memberRepository.save(member);
		
	}

	@Transactional
	@Override
	public void delete(Long memberId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePassword(@RequestParam String email, @ModelAttribute PasswordRequestDto passwordRequestDto) throws Exception {
		
		Optional<Member> omember=  memberRepository.findByEmail(email);
		System.out.println(" : :  : = = = " + omember.get().getEmail());
		System.out.println(email);
		Member member = omember.get();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) throws Exception {
		Optional<Member> omem = memberRepository.findById(memberId);
		if(omem.isEmpty())throw new Exception("멤버 없음");
		return omem.get();
	}

}

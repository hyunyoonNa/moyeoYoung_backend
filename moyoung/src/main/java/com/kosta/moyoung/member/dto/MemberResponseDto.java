package com.kosta.moyoung.member.dto;

import java.time.LocalDate;

import com.kosta.moyoung.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberResponseDto {
	
	private Long memberId;
	private String email;
	private String nickname;
	private String fileName;
	private String profileContent;
	private LocalDate regdate;
	
	public static MemberResponseDto of(Member member) {
		return MemberResponseDto.builder()
				.memberId(member.getMemberId())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.profileContent(member.getProfileContent())
				.fileName(member.getFileName())
				.regdate(member.getRegdate())
				.build();
	}
	
	public static MemberResponseDto toMember(Member member) {
	    return new MemberResponseDto(
	    	member.getMemberId(),
	        member.getEmail(),
	        member.getNickname(),
	        member.getProfileContent(),
	        member.getFileName(),
	        member.getRegdate()
	    );
	}
}

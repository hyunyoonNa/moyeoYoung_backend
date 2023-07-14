package com.kosta.moyoung.member.dto;

import java.time.LocalDate;

import com.kosta.moyoung.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
	
	private String email;
	private String nickname;
	private Long memberId;
	private String imageUrl;
	
	public static MemberResponseDto of(Member member) {
		return MemberResponseDto.builder()
				.memberId(member.getMemberId())
				.imageUrl(member.getImageUrl())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.build();
	}
}

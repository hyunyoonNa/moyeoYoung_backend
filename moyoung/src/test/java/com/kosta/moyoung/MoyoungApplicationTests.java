package com.kosta.moyoung;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;

@SpringBootTest
class MoyoungApplicationTests {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	public void test() {
		Long memberId = JwtUtil.getCurrentMemberId();
		Optional<Member> member = memberRepository.findById(memberId);
		System.out.println(member);
	}

}

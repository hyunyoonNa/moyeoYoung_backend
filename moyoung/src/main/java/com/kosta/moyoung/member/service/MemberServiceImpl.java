package com.kosta.moyoung.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Bookmark;
import com.kosta.moyoung.openroom.entity.Enterance;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.BookmarkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

//	private final MemberService memberService;
	private final MemberRepository memberRepository;
//	private final ModelMapper modelMapper;

	@Autowired
	private BookmarkRepository bookmarkRepository;
	
	@Transactional
	@Override
	public MemberResponseDto findMemberInfoById(Long memberId) throws Exception {
		return memberRepository.findById(memberId).map(MemberResponseDto::of)
				.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}

	@Transactional
	@Override
	public MemberResponseDto findMemberInfoByEmail(String email) throws Exception {
		return memberRepository.findByEmail(email).map(MemberResponseDto::of)
				.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
	}

	@Override
	public void updateMember(Long memberId, MemberRequestDto memberRequestDto, MultipartFile fileName)
			throws Exception {
		Optional<Member> omember = memberRepository.findById(memberId);
		Member member = omember.get();

		member.setFileName(memberRequestDto.getFileName());
		member.setNickname(memberRequestDto.getNickname());
		member.setProfileContent(memberRequestDto.getProfileContent());
		memberRepository.save(member);

	}

	@Transactional
	@Override
	public void deleteMember(Long memberId) throws Exception {
		Optional<Member> omember = memberRepository.findById(memberId);
		System.out.println(memberId);
		Member member = omember.orElseThrow(() -> new RuntimeException("해당하는 회원정보를 찾을 수 없습니다."));
		System.out.println(member);
		memberRepository.delete(member);
	}

	@Override
	public void updatePassword(@RequestParam String email, @ModelAttribute PasswordRequestDto passwordRequestDto)
			throws Exception {

		Optional<Member> omember = memberRepository.findByEmail(email);
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
		if (omem.isEmpty())
			throw new Exception("멤버 없음");
		return omem.get();
	}

	@Override
	public MemberResponseDto findMemberInfoByNickname(String nickname) throws Exception {
		Optional<Member> omember = memberRepository.findByNickname(nickname);
		if (omember.isPresent()) {
			Member member = omember.get();
			return MemberResponseDto.of(member);
		} else {
			throw new Exception("해당 닉네임으로 조회된 회원이 없습니다.");
		}
	}
	
	
	//북마크한 방 리스트 
	@Override 
	public List<RoomDTO> roomListWithBookmark(Long memberId) throws Exception {
		List<RoomDTO> list = new ArrayList<>(); 
		List<Bookmark> bList = bookmarkRepository.findByMemberBookmarkMemberId(memberId);
		System.out.println(bList.size());
		for(Bookmark b : bList) {
			list.add(new RoomDTO(b.getRoomBookmark()));
		} 
		return list; 
	}

	//개설한 방 목록
	@Override
	public List<RoomDTO> madeRoomList(Long memberId) throws Exception {
		List<RoomDTO> list = new ArrayList<>();
		Member member = findMember(memberId);
		List<Room> roomList = member.getMadeRooms();
		for(Room r:roomList) {
			list.add(new RoomDTO(r)); 
		} 
		return list;
	}


	@Override
	public List<RoomDTO> joinRoomList(Long memberId) throws Exception {
		List<RoomDTO> list = new ArrayList<>();
		Member member = findMember(memberId);
		List<Enterance> entList = member.getJoindRooms();
		for(Enterance e:entList) { 
			list.add(new RoomDTO(e.getRoom())); 
		} 
		return list;
	}


}

package com.kosta.moyoung.openroom.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Enterance;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.EnteranceRepository;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;
 

@Service
public class EnteranceServiceImpl implements EnteranceService{ 
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private EnteranceRepository entRepository;
	@Autowired
	private OpenRoomRepository roomRepository;  

	@Override
	public void JoinRoom(Long roomId, Member mem, boolean isHost) throws Exception {  
		Optional<Room> oroom = roomRepository.findById(roomId); 
		if(oroom.isEmpty())throw new Exception("방을 찾을 수 없습니다!"); 
		Room room = oroom.get();
		room.setRoomUserCnt(room.getRoomUserCnt()+1);
		roomRepository.save(room); 
		
		Date today = new Date(System.currentTimeMillis());  
		
		Enterance ent = new Enterance(today, oroom.get(),mem,isHost);
		entRepository.save(ent); 
	}

	@Override
	public List<MemberResponseDto> findEnteranceList(Long roomId) throws Exception {
		List<MemberResponseDto> memList = new ArrayList<>();
		List<Enterance> entList = entRepository.findByRoomRoomId(roomId); 
		for(Enterance ent : entList) {  
			memList.add(MemberResponseDto.of(ent.getMember())); 
		}
		return memList; 
	}

	@Override
	public void leaveRoom(Long roomId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RoomDTO> joinRoomList(Long memberId) throws Exception { 
		List<RoomDTO> dtoList = new ArrayList<>(); 
		//멤버아디로 가입테이블에서 룸아이디 찾기
		List<Enterance> entList  = entRepository.findByMemberMemberId(memberId); 
		//아이디에 해당하는 방 가져오기
			for(Enterance ent : entList) {
				dtoList.add(new RoomDTO(ent.getRoom()));
			}
		
		return dtoList;
	}
	
	
	
}

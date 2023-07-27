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
import com.kosta.moyoung.notification.entity.NotificationType;
import com.kosta.moyoung.notification.service.NotificationService;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Enterance;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.EnteranceRepository;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;

@Service
public class EnteranceServiceImpl implements EnteranceService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private EnteranceRepository entRepository;
	@Autowired
	private OpenRoomRepository roomRepository;  
	@Autowired
	private NotificationService notificationService;

	@Override
	public String JoinRoom(Long roomId, Member mem, boolean isHost) throws Exception {
		String str = "가입이 완료되었습니다!";
		Optional<Room> oroom = roomRepository.findById(roomId);
		if (oroom.isEmpty())
			throw new Exception("방을 찾을 수 없습니다!");
		Room room = oroom.get();
		if (!isHost && room.getRoomType().equals("open")) {
			room.setRoomUserCnt(room.getRoomUserCnt() + 1);
		}
		
	  
		roomRepository.save(room);

		Date today = new Date(System.currentTimeMillis());
		boolean isWaiting = false;
		if (room.getRoomType().equals("close")&&!isHost) {
			str = "가입 신청이 완료되었습니다!";
			isWaiting = true;
		}
		System.out.println(isWaiting);
		Enterance ent = new Enterance(today, oroom.get(), mem, isWaiting);
		entRepository.save(ent);
    notificationService.createNotification( mem, room.getHost(), NotificationType.NEW_ROOM_JOIN, mem.getNickname()+"님이 방에 가입을 신청했습니다.", room.getRoomId());
		return str;
	}

	@Override
	public List<MemberResponseDto> findEnteranceList(Long roomId, boolean isWaiting) throws Exception {
		List<MemberResponseDto> memList = new ArrayList<>();
		List<Enterance> entList = entRepository.findByRoomRoomIdAndStatus(roomId, isWaiting);
		for (Enterance ent : entList) {
			memList.add(MemberResponseDto.of(ent.getMember()));
		}
		return memList;
	} 

	@Override
	public void leaveRoom(Long roomId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletemember(Long memberId, Long roomId) {
		try {
			entRepository.deleteByMemberIdAndRoomId(memberId, roomId);
			Optional<Room> oroom = roomRepository.findById(roomId);
			Room room = oroom.get();
			room.setRoomUserCnt(room.getRoomUserCnt() - 1);
			roomRepository.save(room);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void approveMember(Long memberId, Long roomId) throws Exception {  
		List<Enterance> oent = entRepository.findByMemberMemberIdAndRoomRoomId(memberId, roomId);
		if(oent.isEmpty()) {
			throw new Exception("다시 시도하세요.");
		}
			Enterance ent = oent.get(0);
			ent.setStatus(false);
			entRepository.save(ent);
			
			//방 멤버수 +1
			Optional<Room> room = roomRepository.findById(roomId);
			room.get().setRoomUserCnt(room.get().getRoomUserCnt()+1);
			roomRepository.save(room.get());
		
	}

	@Override
	public void rejectMember(Long memberId, Long roomId) throws Exception { 
		int result = entRepository.deleteByMemberIdAndRoomId(memberId, roomId);
		if(result==0)throw new Exception("다시 시도하세요.");
	}

}

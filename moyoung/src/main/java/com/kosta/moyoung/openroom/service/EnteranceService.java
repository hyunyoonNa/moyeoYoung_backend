package com.kosta.moyoung.openroom.service;

import java.util.List;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.openroom.dto.RoomDTO;

public interface EnteranceService{
	public void JoinRoom(Long roomId)throws Exception;
	public List<MemberResponseDto> findEnteranceList(Long roomId) throws Exception;
//	public EnteranceDTO findEnterance() throws Exception;
	public List<RoomDTO> joinRoomList(Long memberId) throws Exception;
	public void leaveRoom(Long roomId) throws Exception;
	
}

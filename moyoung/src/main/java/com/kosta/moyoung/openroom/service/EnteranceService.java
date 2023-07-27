package com.kosta.moyoung.openroom.service;

import java.util.List;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.dto.RoomDTO;

public interface EnteranceService{
	public String JoinRoom(Long roomId, Member mem, boolean isHost)throws Exception;
	public List<MemberResponseDto> findEnteranceList(Long roomId,boolean isWaiting) throws Exception; 
	public void deletemember (Long memberId, Long roomId);
	public void leaveRoom(Long roomId) throws Exception;
	//가입승낙,거절
	public void approveMember(Long memberId, Long roomId) throws Exception;
	public void rejectMember(Long memberId, Long roomId) throws Exception;
}

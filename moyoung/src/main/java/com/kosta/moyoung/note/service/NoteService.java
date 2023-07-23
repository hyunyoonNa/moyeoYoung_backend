package com.kosta.moyoung.note.service;

import java.util.List;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.note.dto.NoteDto;

public interface NoteService {
	//쪽지쓰기
	void write(NoteDto noteDto) throws Exception;
	//받은쪽지함
	List<NoteDto> receiveNote(Member member) throws Exception;
	
	NoteDto detailtNote(Long noteId) throws Exception;
	//받은편지삭제
	Object deleteNoteByReceiver(Long noteId , Member member) throws Exception;
	//보낸쪽지함
	List<NoteDto> sentNote(Member member) throws Exception;
	//보낸쪽지삭제
	Object deleteNoteBySender(Long noteId, Member member) throws Exception;
	
	//쪽지상태업데이트
	void noteStatus (Long noteId) throws Exception;

}

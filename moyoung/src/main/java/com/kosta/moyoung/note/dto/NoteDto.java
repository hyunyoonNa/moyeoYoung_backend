package com.kosta.moyoung.note.dto;

import java.time.LocalDateTime;

import com.kosta.moyoung.note.entity.Note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
	
	private Long noteId;
	private String content;
	private String senderNickname;
	private String receiverNickname;
	private String sendDate;
	private String status;
	
	public static NoteDto toNote(Note note) {
		return new  NoteDto(
				note.getNoteId(),
				note.getContent(),
				note.getSender().getNickname(),
				note.getReceiver().getNickname(),
				note.getSendDate(),
				note.getStatus()
			);
		
	}
	
	
}

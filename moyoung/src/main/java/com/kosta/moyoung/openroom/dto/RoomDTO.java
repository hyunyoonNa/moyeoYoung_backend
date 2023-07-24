package com.kosta.moyoung.openroom.dto;
 	

import java.sql.Date;

import com.kosta.moyoung.openroom.entity.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomDTO {
	private Long roomId; //방 아이디
	private String roomTitle; //제목
	private String roomContent; //소개글
	private String roomImage; //이미지제목
	private String roomCategory; //카테고리
	private Date roomCreateDate; //수정일
	private String roomType; //모임유형 :open/close
	private Long memberId; //방장아이디
	private Long roomUserCnt;  //멤버수 
	
	public RoomDTO(Room room) {
		this.roomId = room.getRoomId();
		this.roomTitle = room.getRoomTitle();
		this.roomContent = room.getRoomContent();
		this.roomImage = room.getRoomImage(); 
		this.roomCategory = room.getRoomCategory();
		this.roomCreateDate = room.getRoomCreateDate();
		this.roomType = room.getRoomType();
		this.memberId = room.getHost().getMemberId();
		this.roomUserCnt = room.getRoomUserCnt();
		
		
		
	}
}

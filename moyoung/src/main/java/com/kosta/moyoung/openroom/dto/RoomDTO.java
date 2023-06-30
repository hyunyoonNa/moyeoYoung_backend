package com.kosta.moyoung.openroom.dto;
 	

import java.sql.Date;

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
	private Long userId; //방장아이디
	private Long roomUserCnt;  //멤버수 
}

package com.kosta.moyoung.openroom.dto;

import java.sql.Date;

import com.kosta.moyoung.openroom.entity.Enterance;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
@NoArgsConstructor
public class EnteranceDTO {
	private Long enteranceId;
	private Date entRegDate;
	private Long roomId;
	private Long memberId;
	private boolean status;
	 
	public EnteranceDTO(Enterance ent) {
		this.enteranceId = ent.getEnteranceId();
		this.entRegDate = ent.getEntRegDate();
		this.roomId = ent.getRoom().getRoomId();
		this.memberId = ent.getMember().getMemberId();
		this.status = ent.isStatus();
	}
}

package com.kosta.moyoung.feedroom.dto;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kosta.moyoung.openroom.dto.RoomDTO;
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
public class RoomFeedDTO {
	private Long feedId;
	private Long userId;
	private Long roomId;
	private String title;
	private String content;
	private String filename;
	private Date roomCreateDate;
}

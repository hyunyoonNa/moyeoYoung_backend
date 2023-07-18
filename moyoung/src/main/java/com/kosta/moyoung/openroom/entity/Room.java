package com.kosta.moyoung.openroom.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.dto.RoomDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter 
@NoArgsConstructor 
public class Room {
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roomId; //방 아이디
	@Column(nullable = false, length = 17)
	private String roomTitle; //제목
	@Column(nullable = false, length = 300)
	private String roomContent; //소개글
	@Column(nullable = false)
	private String roomImage; //이미지제목
	@Column(nullable = false)
	private String roomCategory; //카테고리
	@Column(nullable = false)
	private Date roomCreateDate; //생성일-수정일
	@Column(nullable = false)
	private String roomType; //모임유형 : open/close비공개 
	
	@Column(nullable = false)
	private Long roomUserCnt;  //멤버수
	
	@OneToMany(mappedBy="roomBookmark" , fetch=FetchType.LAZY)
	private List<Bookmark> bookmarks = new ArrayList<>();
	
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
	private List<RoomfeedEntity> roomfeeds = new ArrayList<>();
	   
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="memberId")  
	private Member member; //방장아이디
//	private Long memberId;
	
	@Builder 
	public Room(Long roomId, String roomTitle, String roomContent, String roomImage, String roomCategory,
			Date roomCreateDate, String roomType, Long roomUserCnt) {
		super();
		this.roomId = roomId;
		this.roomTitle = roomTitle;
		this.roomContent = roomContent;
		this.roomImage = roomImage;
		this.roomCategory = roomCategory;
		this.roomCreateDate = roomCreateDate;
		this.roomType = roomType; 
		this.roomUserCnt = roomUserCnt;
	}
	 //방만들때
	public Room(RoomDTO roomDto, Member member) {
		this.roomId = roomDto.getRoomId();
		this.roomTitle = roomDto.getRoomTitle();
		this.roomContent = roomDto.getRoomContent();
		this.roomImage = roomDto.getRoomImage(); 
		this.roomCategory = roomDto.getRoomCategory();
		this.roomCreateDate = roomDto.getRoomCreateDate();
		this.roomType = roomDto.getRoomType();
		this.member = member;
		this.roomUserCnt = roomDto.getRoomUserCnt();
	}
	
}


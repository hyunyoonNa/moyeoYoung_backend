package com.kosta.moyoung.feedroom.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.entity.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name="feed")
public class RoomfeedEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long feedId;
	
	@ManyToOne(fetch=FetchType.EAGER)	
	@JoinColumn(name ="memberId")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name ="roomId")
	private Room room;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	@Column
	private Date roomCreateDate;
	
	@Builder
	public static RoomfeedEntity createRoomfeedEntity(Member member, Room room, String content, String title) {
	    RoomfeedEntity roomfeedEntity = new RoomfeedEntity();
	    roomfeedEntity.setMember(member);
	    System.out.println(member);
	    roomfeedEntity.setRoom(room);
	    roomfeedEntity.setContent(content);
	    roomfeedEntity.setTitle(title);
	    return roomfeedEntity;
	}
}

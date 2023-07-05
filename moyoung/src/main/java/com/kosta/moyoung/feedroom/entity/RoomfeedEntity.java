package com.kosta.moyoung.feedroom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kosta.moyoung.openroom.entity.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name="feed")
public class RoomfeedEntity extends BaseTimeEntity {
	@Id
	@GeneratedValue
	private Long feedId;
	@Column(nullable = false)
	private Long userId;
	@ManyToOne
	@JoinColumn(name ="roomId")
	private Room room;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String content;
	
	@Builder
	public static RoomfeedEntity createRoomfeedEntity(Long feedId, Long userId, Room room, String content, String title) {
	    RoomfeedEntity roomfeedEntity = new RoomfeedEntity();
	    roomfeedEntity.setFeedId(feedId);
	    roomfeedEntity.setUserId(userId);
	    roomfeedEntity.setRoom(room);
	    roomfeedEntity.setContent(content);
	    roomfeedEntity.setTitle(title);
	    return roomfeedEntity;
	}
	
}

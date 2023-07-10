package com.kosta.moyoung.openroom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kosta.moyoung.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter 
@NoArgsConstructor 
public class Bookmark {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookmarkId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="memberId")
	private Member memberBookmark;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roomId")
	private Room roomBookmark;
		
	@Builder
	public Bookmark(Member member, Room room) {
		memberBookmark = member;
		roomBookmark = room;
	} 
}

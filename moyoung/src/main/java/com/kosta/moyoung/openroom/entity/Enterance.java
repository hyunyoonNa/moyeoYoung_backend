package com.kosta.moyoung.openroom.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kosta.moyoung.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enterance {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long entranceId;
	
	@Column
	private Date entRegDate;
	 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roomId")  
	private Room room;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="memberId")  
	private Member member;
		
		
		

	
	
	
}

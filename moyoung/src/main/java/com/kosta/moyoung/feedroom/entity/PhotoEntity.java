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

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name="photo")
public class PhotoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long photoId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name ="feedId")
	private RoomfeedEntity roomfeedentity;
	
	@Column
	private String FileName; 
}

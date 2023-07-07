package com.kosta.moyoung.youthspace.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YouthSpace {
	@Id
	private Integer spaceId;
	@Column
	private String spaceType;
	@Column
	private String title;
	@Column
	private String titleImage;
	@Column
	private String place;
	@Column(length = 3000)
	private String useTime;
	@Column(length = 1000)
	private String telnum;
	@Column(length = 1000)
	private String openHours;
	@Column(length = 1000)
	private String inst;
	@Column(length = 1000)
	private String homepage;
	@Column(length = 1000)
	private String imgs;
	@Column(length = 3000)
	private String detail;
	
}

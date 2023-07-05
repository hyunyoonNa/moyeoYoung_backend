package com.kosta.moyoung.openroom.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
 

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter 
@NoArgsConstructor 
@ToString
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, length = 30)
	private String email;

	@Setter
	@Column(nullable = false, length = 100)
	private String password;

	@Setter
	@Column(nullable = false, length = 20)
	private String nickname;
 
	@OneToMany(mappedBy="memberBookmark")
	private List<Bookmark> bookmarks = new ArrayList<>();
	

	@Builder
	public Member(Long userId, String email, String password, String nickname) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.nickname = nickname; 
	}

}

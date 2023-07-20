package com.kosta.moyoung.feedroom.entity;

import java.sql.Date;
import java.time.LocalDateTime;

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

import lombok.Getter;
import lombok.Setter;




@Entity
@Getter
@Setter
@Table(name="comment")
public class CommentEntity {
	
	@Id
	@Column(name="commentId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long commentId;
	
	@ManyToOne(fetch=FetchType.EAGER)   
    @JoinColumn(name ="member_id")
    private Member member;
	
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private RoomfeedEntity feed;
    
    @Column
    private String comment;

    @Column
    private LocalDateTime CommentCreateDate;
	
}

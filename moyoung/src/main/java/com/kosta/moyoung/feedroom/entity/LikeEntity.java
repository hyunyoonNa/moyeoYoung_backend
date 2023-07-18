package com.kosta.moyoung.feedroom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kosta.moyoung.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "feedId")
    private RoomfeedEntity feed;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
    
    @Builder
    public LikeEntity(Member member, RoomfeedEntity feed) {
        this.member = member;
        this.feed = feed;
    }
}

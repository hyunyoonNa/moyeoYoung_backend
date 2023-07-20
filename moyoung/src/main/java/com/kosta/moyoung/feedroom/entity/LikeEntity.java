package com.kosta.moyoung.feedroom.entity;

import javax.persistence.*;

import com.kosta.moyoung.member.entity.Member;

import lombok.AllArgsConstructor;
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
}

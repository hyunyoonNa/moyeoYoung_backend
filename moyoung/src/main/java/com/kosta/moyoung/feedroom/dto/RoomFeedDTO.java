package com.kosta.moyoung.feedroom.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomFeedDTO {
   private Long feedId;
   private Long memberId;
   private Long roomId;
   private String nickname;
   private String profilename;
   private String title;
   private String content;
   private String filename;
   private Date roomCreateDate;
   private int likeCount = 0; // LikeCount 변수 추가하고 초기값을 0으로 설정
}
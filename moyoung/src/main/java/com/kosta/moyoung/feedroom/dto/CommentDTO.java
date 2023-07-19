package com.kosta.moyoung.feedroom.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class CommentDTO {
	private Long commentId;
	private Long memberId;
	private Long feedId;
	private String comment;
	private LocalDateTime commentCreateDate;
	private String nickname;
	private String profilename;
}

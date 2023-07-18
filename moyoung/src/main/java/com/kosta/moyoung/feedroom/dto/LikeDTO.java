package com.kosta.moyoung.feedroom.dto;

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
public class LikeDTO {
	private Long LikeId;
	private Long feedId;
	private Long memberId;
}

package com.kosta.moyoung.note.dto;

import com.kosta.moyoung.notification.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class NoteSaveDto {

	private Long senderId;

	private Long receiverId;

	private Long targetId;

	private NotificationType type;

	private String detail;
}

package com.kosta.moyoung.notification.entity;

import lombok.Data;

@Data
public class NotificationArgs {
	
	//알림을 발생시킨사람
	private Long fromMemberId;
	
	private Long targetId;
}

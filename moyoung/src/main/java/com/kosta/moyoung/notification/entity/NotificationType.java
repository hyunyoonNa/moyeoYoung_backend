package com.kosta.moyoung.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
	
	NEW_MESSAGE_ON_NOTE("new message"),
    NEW_COMMENT_ON_POST("new comment"),
    NEW_LIKE_ON_POST("new like"),
    NEW_ROOM_JOIN("new room");
	
	private final String value;
}

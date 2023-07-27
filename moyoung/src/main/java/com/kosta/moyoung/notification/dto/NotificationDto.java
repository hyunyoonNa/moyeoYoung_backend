package com.kosta.moyoung.notification.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.notification.entity.Notification;
import com.kosta.moyoung.notification.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
	private Long id;
	@JsonIgnore
    private Member senderId;

    private Long targetId;
    
    private boolean isRead;
    
    private String receiverNickname;
    
    private String senderNickname;

    private NotificationType type;

    private String message;

    private String registerAt;
    
    
    // Notification를 NotificationDto로 변환하는 정적 메서드
    public static NotificationDto toNotification(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getNotificationId())
                .message(notification.getMessage())
                .type(notification.getType())
                .senderNickname(notification.getSenderId().getNickname())
                .receiverNickname(notification.getReceiverId().getNickname())
                .registerAt(notification.getRegisteredAt())
                .isRead(notification.isRead())
                .targetId(notification.getTargetId())
                .senderId(notification.getSenderId())
                .build();
    }

}

package com.kosta.moyoung.notification.dto;

import java.time.LocalDateTime;

import com.kosta.moyoung.notification.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
	
    private Long alarmId;
    private String message;
    private String roomName;
    private LocalDateTime notificationTime;
    private String senderNickname;
    private NotificationType type;
    private Boolean isRead;
    private Boolean isDeleted;
    private String link;
    

    
	
}

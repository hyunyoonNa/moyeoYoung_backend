package com.kosta.moyoung.notification.service;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.notification.dto.NotificationDto;
import com.kosta.moyoung.notification.entity.NotificationType;

public interface NotificationService {
	
	//현재 로그인한 유저에게 읽지 않은 알림을 가져오는 메서드
	List<NotificationDto> getAllNotificationsForReceiver(Member receiver) throws Exception;
	 Boolean markNotificationAsRead(Long notificationId, boolean isRead) throws Exception;
	 
	// 알림 생성 메서드
    void createNotification(Member sender, Member receiver, NotificationType type, String message, Long targetId) throws Exception;
    //알림 삭제
    void deleteNotification(Long notificationId) throws Exception;
    //SSE연결
    SseEmitter connectNotification(Long receiverId) throws Exception;
}

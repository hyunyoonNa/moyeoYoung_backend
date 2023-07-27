package com.kosta.moyoung.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.notification.entity.Notification;
import com.kosta.moyoung.notification.entity.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	 List<Notification> findByReceiverIdAndIsRead(Member receiverId, boolean isRead);
	 List<Notification> findByReceiverId(Member receiverId);
	 List<Notification> findByType(NotificationType type);
}

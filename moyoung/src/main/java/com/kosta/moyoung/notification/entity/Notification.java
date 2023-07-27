package com.kosta.moyoung.notification.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kosta.moyoung.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long notificationId;

	@Column
	private String message;

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	
	//알람을 받은사람
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member receiverId;
	
	@Column
	private String registeredAt;
	
	
	//알람을 발생시킨사람
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member senderId;
	
	
	@Column
	private Long  targetId;
	
	@Column
    private boolean isRead; // 읽음 상태 여부 
	
	
	@PrePersist
	protected  void registeredAt() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm");
		registeredAt = LocalDateTime.now().format(formatter);
		isRead = false; // 새로운 알림이 생성되면 읽음 상태를 false로 초기화
	}
	
	public void read() {
        this.isRead = true;
    }
	

}

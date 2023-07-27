package com.kosta.moyoung.notification.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.notification.dto.NotificationDto;
import com.kosta.moyoung.notification.entity.Notification;
import com.kosta.moyoung.notification.entity.NotificationType;
import com.kosta.moyoung.notification.repository.EmitterRepository;
import com.kosta.moyoung.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final static String ALARM_NAME = "alarm";

	private final NotificationRepository notificationRepository;
	private final EmitterRepository emitterRepository;
	private final ObjectMapper objectMapper;

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; //한시간동안 연결유지

	@Override
	public List<NotificationDto> getAllNotificationsForReceiver(Member receiver) throws Exception {
		// TODO Auto-generated method stub
		List<Notification> notifications = notificationRepository.findByReceiverIdAndIsRead(receiver, false);
		List<NotificationDto> notificationDtos = new ArrayList<>();
		for (Notification notification : notifications) {
			NotificationDto notificationDto = NotificationDto.toNotification(notification);
			notificationDto.setSenderNickname(notification.getSenderId().getNickname());
			notificationDto.setReceiverNickname(notification.getReceiverId().getNickname());
			notificationDtos.add(NotificationDto.toNotification(notification));
		}

		return notificationDtos;
	}

//	@Override
//	public void markNotificationAsRead(Long notificationId) {
//		Notification notification = notificationRepository.findById(notificationId)
//				.orElseThrow(() -> new IllegalArgumentException("해당 알림을 찾을 수 없습니다."));
//		notification.read();
//		notificationRepository.save(notification);
//
//	}

	@Override
	public void createNotification(Member sender, Member receiver, NotificationType type, String message, Long targetId)
			throws Exception {
		// 새로운 알림 엔티티 생성
		Notification notification = Notification.builder().message(message).type(type).receiverId(receiver)
				.senderId(sender).targetId(targetId).build();

		// 알림 저장
		notificationRepository.save(notification);

		// 알림이 생성됐을 때 해당 수신자의 SSE 연결이 있는지 확인하고 데이터 전송
		try {
			emitterRepository.get(receiver.getMemberId()).ifPresent(emitter -> {
				try {
					Map<String, Object> data = new HashMap<>();
					data.put("id", notification.getNotificationId());
					data.put("message", notification.getMessage());
					data.put("type", notification.getType());
					data.put("receiverId", notification.getReceiverId().getMemberId());
					data.put("senderId", notification.getSenderId().getMemberId());
					data.put("targetId", notification.getTargetId());
					data.put("senderNickname", notification.getSenderId().getNickname());
					data.put("receiverNickname", notification.getReceiverId().getNickname());
					data.put("registeredAt", notification.getRegisteredAt());

					String jsonData = objectMapper.writeValueAsString(data);
					emitter.send(SseEmitter.event().data(jsonData));
				} catch (IOException e) {
					// 예외 처리
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			// 예외 처리
			e.printStackTrace();
		}
	}

	@Transactional
	@Override
	public SseEmitter connectNotification(Long receiverId) throws Exception {
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitterRepository.save(receiverId, emitter);
		emitter.onCompletion(() -> emitterRepository.delete(receiverId));
		emitter.onTimeout(() -> emitterRepository.delete(receiverId));

		try {
			// 더미 데이터 생성 및 전송
			Map<String, Object> data = new HashMap<>();
			data.put("id", -1L); // 더미 데이터를 구분하기 위한 임의의 음수값 설정
			data.put("message", "This is a dummy data.");
			data.put("type", "DUMMY");
			data.put("receiverId", receiverId);
			data.put("senderId", -1L); // 더미 데이터를 구분하기 위한 임의의 음수값 설정
			data.put("targetId", -1L); // 더미 데이터를 구분하기 위한 임의의 음수값 설정
			data.put("senderNickname", "System");
			data.put("receiverNickname", "You");
			data.put("registeredAt", LocalDateTime.now().toString());

			String jsonData = objectMapper.writeValueAsString(data);
			emitter.send(SseEmitter.event().data(jsonData));

			emitter.send(SseEmitter.event().id("receiverId").name(ALARM_NAME).data("connect completed"));
		} catch (IOException exception) {
			throw new Exception("SEE CONECT ERROR");
		}
		return emitter;
	}

	@Override
	public void deleteNotification(Long notificationId) throws Exception {

		// 조회
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 쪽지가 없습니다: " + notificationId));

        // 쪽지를 삭제
        notificationRepository.delete(notification);
    }

	@Override
	public Boolean markNotificationAsRead(Long notificationId, boolean isRead) throws Exception {
		 Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
	        if (optionalNotification.isPresent()) {
	            Notification notification = optionalNotification.get();
	            notification.setRead(isRead);
	            notificationRepository.save(notification);
	            return true; // 알림 업데이트 성공을 나타내는 값 반환
	        } else {
	            throw new Exception("Notification not found with ID: " + notificationId);
	        }
	}
}

package com.kosta.moyoung.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.notification.dto.NotificationDto;
import com.kosta.moyoung.notification.service.NotificationService;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotificationController {

	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final NotificationService notificationService;

	/**
	 * @title 로그인 한 유저 sse 연결
	 */
//	@GetMapping(value = "/connect", produces = "text/event-stream")
//    public ResponseEntity<SseEmitter> subscribe(@RequestHeader(value = "Last-Event-ID", required = false) String lastEventId) {
//        try {
//            // 클라이언트와 SSE 연결을 맺음
//            SseEmitter emitter = notificationService.subscribe(lastEventId);
//            return new ResponseEntity<>(emitter, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

	@GetMapping("/list")
	public ResponseEntity<List<NotificationDto>> getNotifications() {
		try {
			// JWT 토큰에서 현재 로그인한 사용자의 ID를 가져옴
			Member receiver = memberService.findMember(JwtUtil.getCurrentMemberId());

			List<NotificationDto> notificationDtos = notificationService.getAllNotificationsForReceiver(receiver);
			return new ResponseEntity<List<NotificationDto>>(notificationDtos, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<NotificationDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> subscribeNotifications(@RequestParam String accessToken) {
		System.out.println("accessToken: " + accessToken);

		try {
			if (accessToken == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 인증되지 않은 사용자는 SSE 연결을 허용하지 않음
			}

			Long currentMemberId = JwtUtil.getCurrentMemberIdFromToken(accessToken);
			if (currentMemberId == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 올바르지 않은 토큰일 경우 인증 실패로 처리
			}

			Member receiver = memberService.findMember(currentMemberId);
			SseEmitter emitter = notificationService.connectNotification(receiver.getMemberId());
			return new ResponseEntity<>(emitter, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	 @CrossOrigin(origins = "http://localhost:3000")
	  @PutMapping("/read/{notificationId}")
	  public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
	    try {
	      boolean isRead = true; // 알림을 읽은 상태로 업데이트
	      Boolean updateResult = notificationService.markNotificationAsRead(notificationId, isRead);
	      if (updateResult) {
	        return ResponseEntity.ok("알림을 읽음.");
	      } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("알림을 찾을 수 없음.");
	      }
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알림 업데이트 실패: " + e.getMessage());
	    }
	  }


	@DeleteMapping("/delete/{notificationId}")
	public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
		try {
			notificationService.deleteNotification(notificationId);
			return ResponseEntity.ok("쪽지가 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("쪽지 삭제 중 오류가 발생했습니다.");
		}
	}

}

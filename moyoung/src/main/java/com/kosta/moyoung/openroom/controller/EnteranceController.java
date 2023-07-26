package com.kosta.moyoung.openroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.openroom.repository.EnteranceRepository;
import com.kosta.moyoung.openroom.service.EnteranceService;
import com.kosta.moyoung.openroom.service.OpenRoomService;
import com.kosta.moyoung.security.jwt.JwtUtil;

@RestController
@RequestMapping("room") 
public class EnteranceController {
	@Autowired
	private EnteranceService enteranceService;
	@Autowired
	private MemberService memberService;  
	@PostMapping("/joinRoom") 
	public ResponseEntity<String> joinRoom(@RequestBody Map<String,String> map) {
		try {
			Member mem = memberService.findMember(JwtUtil.getCurrentMemberId()); 
			String str = enteranceService.JoinRoom(Long.valueOf(map.get("roomId")),mem,false);
				return new ResponseEntity<String>(str,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}

	//방 멤버 리스트
	@GetMapping("/memberList/{roomId}") 
	public ResponseEntity<Map<String,Object>> roomMemberList(@PathVariable Long roomId) { 
		Map<String, Object> res = new HashMap<>();
		try { 
			List<MemberResponseDto> list = enteranceService.findEnteranceList(roomId, false);
			List<MemberResponseDto> waitingList = enteranceService.findEnteranceList(roomId, true);
			System.out.println(list);
			System.out.println(waitingList);
			res.put("list", list); 
			res.put("waitingList",waitingList);
			res.put("logInId", JwtUtil.getCurrentMemberId());
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("deletemember/{memberId}/{roomId}")
	public ResponseEntity<String> deletemember(@PathVariable("memberId") Long memberId, @PathVariable("roomId") Long roomId) {
		try {
			enteranceService.deletemember(memberId, roomId);
			return new ResponseEntity<String> ("강퇴 완료", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//방 가입 수락
	@PutMapping("/approveMember")
	public ResponseEntity<String> approveMember(@RequestBody Map<String,Long> map) {
		try {
			enteranceService.approveMember(map.get("memberId"), map.get("roomId"));
			return new ResponseEntity<String> ("가입 요청을 승낙했습니다!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	//방 가입 거절
	@PostMapping("/rejectMember")
	public ResponseEntity<String> rejectMember(@RequestBody Map<String,Long> map) {
		try {
			enteranceService.rejectMember(map.get("memberId"), map.get("roomId"));
			return new ResponseEntity<String> ("가입 요청을 거절했습니다!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
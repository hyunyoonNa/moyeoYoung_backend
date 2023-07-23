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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.service.EnteranceService;
import com.kosta.moyoung.security.jwt.JwtUtil;

@RestController
@RequestMapping("room") 
public class EnteranceController {
	@Autowired
	private EnteranceService enteranceService;
	
	@PostMapping("/joinRoom") 
	public ResponseEntity<String> joinRoom(@RequestBody Map<String,Long> map) {
		try {
			enteranceService.JoinRoom(map.get("roomId"));
			return new ResponseEntity<String>("가입되었습니다!",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
			
		}
	}

	@GetMapping("/memberList/{roomId}") 
	public ResponseEntity<Map<String,Object>> roomMemberList(@PathVariable Long roomId) { 
		Map<String, Object> res = new HashMap<>();
		try { 
			List<MemberResponseDto> list = enteranceService.findEnteranceList(roomId); 
			res.put("list", list);
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			
		}
	}
	//마이페이지 가입한 방 보기
	@GetMapping("/joinRoomList") 
	public ResponseEntity<Map<String,Object>> joinRoomList() { 
		Map<String, Object> res = new HashMap<>();
		try { 
			List<RoomDTO> list = enteranceService.joinRoomList(JwtUtil.getCurrentMemberId()); 
			res.put("list", list);
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			
		}
	}
}
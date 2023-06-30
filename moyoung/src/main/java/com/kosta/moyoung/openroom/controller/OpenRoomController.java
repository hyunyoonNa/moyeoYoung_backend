package com.kosta.moyoung.openroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.service.OpenRoomService;

@RestController
public class OpenRoomController {
	@Autowired
	private OpenRoomService orService;
	
	@PostMapping("/makeRoom")
	public ResponseEntity<String> makeRoom(@ModelAttribute RoomDTO roomDto, 
			@RequestParam(value = "file", required=false) MultipartFile file){

//		System.out.println(roomDto);
		try {
			orService.makeRoom(roomDto,file);
			return new ResponseEntity<String>("모임방 개설 성공!",HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("모임방 개설 실패 ㅠ.ㅠ",HttpStatus.BAD_REQUEST);
		}
	}
}

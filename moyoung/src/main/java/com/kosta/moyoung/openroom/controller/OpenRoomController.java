package com.kosta.moyoung.openroom.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.service.OpenRoomService;

@RestController
public class OpenRoomController {
	@Autowired
	private OpenRoomService orService;
	
	@PostMapping("/makeRoom")
	public ResponseEntity<Long> makeRoom(@ModelAttribute RoomDTO roomDto, 
			@RequestParam(value = "file", required=false) MultipartFile file){
		try {
			Long roomId = orService.makeRoom(roomDto,file);  
			return new ResponseEntity<Long>(roomId,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getroomMain/{roomId}")
	public ResponseEntity<Room> selectRoomById(@PathVariable Long roomId){
		System.out.println(roomId);
		try {
			Room room = orService.selectById(roomId);
			System.out.println(room);
			return new ResponseEntity<Room>(room, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<Room>(HttpStatus.BAD_REQUEST);
		}
	}
}

package com.kosta.moyoung.openroom.service;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;

public interface OpenRoomService {
	
	void makeRoom(RoomDTO roomDto,MultipartFile file)throws Exception;
}

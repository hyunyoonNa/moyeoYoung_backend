package com.kosta.moyoung.openroom.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;

public interface OpenRoomService {
	Long makeRoom(RoomDTO roomDto,MultipartFile file)throws Exception;
	Room selectById(Long id) throws Exception;
}

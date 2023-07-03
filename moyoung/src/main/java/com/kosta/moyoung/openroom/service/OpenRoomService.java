package com.kosta.moyoung.openroom.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;

public interface OpenRoomService {
	
	void makeRoom(RoomDTO roomDto,MultipartFile file)throws Exception;
	List<Room> findRoomList() throws Exception;
	List<Room> fineRoomByCategory(String cateName) throws Exception;
	List<Room> fineRoomByWord(String word) throws Exception;
	void fileView(String imgName,OutputStream out) throws Exception;
}	

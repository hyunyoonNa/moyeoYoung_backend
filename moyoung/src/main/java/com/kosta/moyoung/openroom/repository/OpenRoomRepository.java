package com.kosta.moyoung.openroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;

public interface OpenRoomRepository extends JpaRepository<Room, Long> {
	
}

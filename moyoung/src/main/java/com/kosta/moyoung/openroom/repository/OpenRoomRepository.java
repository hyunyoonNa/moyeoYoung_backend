package com.kosta.moyoung.openroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.moyoung.openroom.entity.Room;

public interface OpenRoomRepository extends JpaRepository<Room, Long> {
 
	@Query(value="SELECT * FROM room  WHERE room_category = ?1", nativeQuery = true) 
	List<Room> findAllByRoomCategory(String cateName)throws Exception;
	
	@Query(value="SELECT * FROM room  WHERE room_title like %?1%", nativeQuery = true) 
	List<Room> findAllByRoomWord(String word)throws Exception;
	
	
}

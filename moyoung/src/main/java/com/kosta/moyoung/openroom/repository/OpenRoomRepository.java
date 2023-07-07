package com.kosta.moyoung.openroom.repository;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
import com.kosta.moyoung.openroom.entity.Room;

public interface OpenRoomRepository extends JpaRepository<Room, Long> {
 
	@Query(value="SELECT * FROM room  WHERE room_category = ?1", nativeQuery = true) 
	Page<Room> findAllByRoomCategory(String cateName,  PageRequest pageRequest )throws Exception;
	
	@Query(value="SELECT * FROM room WHERE room_title LIKE CONCAT('%', ?1, '%')", nativeQuery = true) 
	Page<Room> findAllByRoomWord(String word, PageRequest pageRequest)throws Exception;
	 
	
}

package com.kosta.moyoung.openroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.moyoung.openroom.entity.Enterance;

public interface EnteranceRepository extends JpaRepository<Enterance, Long> {

//	@Query(value="SELECT * FROM enterance WHERE room_id=?", nativeQuery = true) 
//	List<Enterance> findAllByRoomId(Long roomId)throws Exception; 
//	
	List<Enterance> findByRoomRoomId(Long roomId)throws Exception; 
	List<Enterance> findByMemberMemberId(Long memberId)throws Exception; 
}

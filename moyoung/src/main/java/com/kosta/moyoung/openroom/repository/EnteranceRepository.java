package com.kosta.moyoung.openroom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.moyoung.openroom.entity.Enterance;

public interface EnteranceRepository extends JpaRepository<Enterance, Long> {
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM enterance WHERE member_id=?1 AND room_id=?2", nativeQuery = true) 
	int deleteByMemberIdAndRoomId(Long memberId, Long roomId)throws Exception; 
//	
	List<Enterance> findByRoomRoomIdAndStatus(Long roomId,boolean status)throws Exception; 
	List<Enterance> findByMemberMemberId(Long memberId)throws Exception; 
	
	List<Enterance> findByMemberMemberIdAndRoomRoomId(Long memberId,Long roomId)throws Exception;
	 
}

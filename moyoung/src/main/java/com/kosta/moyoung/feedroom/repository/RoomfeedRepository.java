package com.kosta.moyoung.feedroom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;

public interface RoomfeedRepository extends JpaRepository<RoomfeedEntity, Long> {
   @Query(value="SELECT * FROM feed WHERE room_id = ?1 ORDER BY feed_id DESC", nativeQuery = true) 
   List<RoomfeedEntity> findAllByRoomByRoomID(Long roomId)throws Exception;
}
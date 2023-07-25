package com.kosta.moyoung.openroom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.util.PageInfo;

public interface OpenRoomService { 
	Long makeRoom(RoomDTO roomDto,MultipartFile file, Member member)throws Exception;
	List<RoomDTO> findRoomList(Integer page, PageInfo pageInfo, Integer cnt) throws Exception;
	List<RoomDTO> fineRoomByCategory(String cateName,Integer page, PageInfo pageInfo) throws Exception;
	List<RoomDTO> fineRoomByWord(String word,Integer page, PageInfo pageInfo) throws Exception;
	RoomDTO selectById(Long id) throws Exception;
	Boolean bookMark(Long roomId, Long memberId)throws Exception;
	List<Long> isBookmarks(Long memberId) throws Exception;  
	void removeRoom(Long roomId) throws Exception; 
}	


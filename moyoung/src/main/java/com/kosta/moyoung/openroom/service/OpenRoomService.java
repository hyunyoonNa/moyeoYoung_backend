package com.kosta.moyoung.openroom.service;

import java.util.Optional;
import java.io.OutputStream;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.util.UserPrincipal;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Bookmark;
import com.kosta.moyoung.util.PageInfo;

public interface OpenRoomService { 
	void makeRoom(RoomDTO roomDto,MultipartFile file)throws Exception;
	List<RoomDTO> findRoomList(Integer page, PageInfo pageInfo) throws Exception;
	List<RoomDTO> fineRoomByCategory(String cateName,Integer page, PageInfo pageInfo) throws Exception;
	List<RoomDTO> fineRoomByWord(String word,Integer page, PageInfo pageInfo) throws Exception;
	void fileView(String imgName,OutputStream out) throws Exception; 
	Boolean bookMark(Long roomId, Long memberId)throws Exception;
	List<Long> isBookmarks(Long memberId) throws Exception; 
}	


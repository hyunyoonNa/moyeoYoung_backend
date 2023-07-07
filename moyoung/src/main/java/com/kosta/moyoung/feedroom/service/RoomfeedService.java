package com.kosta.moyoung.feedroom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;

public interface RoomfeedService {
	void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception;
}

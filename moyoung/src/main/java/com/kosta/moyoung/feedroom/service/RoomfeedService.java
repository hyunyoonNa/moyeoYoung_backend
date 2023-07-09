package com.kosta.moyoung.feedroom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;

public interface RoomfeedService {
   void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception;
   List<RoomfeedEntity> selectFeed(Long roomid) throws Exception;
   Optional<RoomfeedEntity> detailFeed(Long feedId) throws Exception;
}
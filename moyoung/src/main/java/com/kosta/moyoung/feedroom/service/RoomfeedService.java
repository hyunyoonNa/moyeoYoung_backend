package com.kosta.moyoung.feedroom.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;

public interface RoomfeedService {
   void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception;
   List<RoomFeedDTO> selectFeed(Long roomid) throws Exception;
   RoomFeedDTO detailFeed(Long feedId) throws Exception;
   void feedFileView(String imgName,OutputStream out) throws Exception; 
   void deletefeed(Long feedId) throws Exception;
   void modifyFeed(Long feedId, RoomFeedDTO roomfeedDto) throws Exception;
}
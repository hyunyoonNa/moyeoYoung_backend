package com.kosta.moyoung.feedroom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.feedroom.repository.RoomfeedRepository;
import com.kosta.moyoung.member.dto.TokenDto;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
@Service
public class RoomfeedServiceImpl implements RoomfeedService {
   
   @Autowired
   private ModelMapper modelMapper; 
   
   @Autowired
   private RoomfeedRepository rfrepository;
   
   @Autowired
   private OpenRoomRepository oprepository;
   
   private String dir = "C:/resources/upload/";

   @Override
   public void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception {
      Date today = new Date(System.currentTimeMillis());
        roomfeedDto.setRoomCreateDate(today);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String filenames = "";
		if (files != null && files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && !files[i].isEmpty()) {
					String fileName = files[i].getOriginalFilename();
					System.out.println(fileName);
					File dfile = new File(dir + fileName);
					files[i].transferTo(dfile);
				}
			}
		}
		
      RoomfeedEntity roomfeed = modelMapper.map(roomfeedDto, RoomfeedEntity.class);
      rfrepository.save(roomfeed);
   }

   @Override
   public List<RoomFeedDTO> selectFeed(Long roomId) throws Exception {
	   Optional<Room> room = oprepository.findById(roomId);
	   List<RoomfeedEntity> Rfeeds = room.get().getRoomfeeds();
	   
	   Rfeeds.sort(Comparator.comparing(RoomfeedEntity::getFeedId).reversed()); // 최신순으로
	   
	   List<RoomFeedDTO> feeds = new ArrayList<>();
	   for (RoomfeedEntity roomFeedEntity : Rfeeds) {
	        RoomFeedDTO roomFeedDTO = new RoomFeedDTO();
	        roomFeedDTO.setTitle(roomFeedEntity.getTitle());
	        roomFeedDTO.setContent(roomFeedEntity.getContent());
	        roomFeedDTO.setFeedId(roomFeedEntity.getFeedId());
	        roomFeedDTO.setFilename(roomFeedEntity.getFilename());
	        roomFeedDTO.setRoomCreateDate(roomFeedEntity.getRoomCreateDate());
	        feeds.add(roomFeedDTO);
	   }
	   return feeds;
   }

	@Override
	public RoomFeedDTO detailFeed(Long feedId) throws Exception {
		Optional<RoomfeedEntity> rfeed = (rfrepository.findById(feedId));
		RoomFeedDTO feed = new RoomFeedDTO();
		feed.setContent(rfeed.get().getContent());
		feed.setFilename(rfeed.get().getFilename());
		feed.setTitle(rfeed.get().getTitle());
		feed.setFeedId(rfeed.get().getFeedId());
		return feed;
	}
	
	@Override 
	public void feedFileView(String imgName,OutputStream out) throws Exception { 
		FileInputStream fis = new FileInputStream(dir + imgName);
		FileCopyUtils.copy(fis, out);
		out.flush();  
	}
}


package com.kosta.moyoung.feedroom.service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.feedroom.repository.RoomfeedRepository;
import com.kosta.moyoung.member.dto.TokenDto;
@Service
public class RoomfeedServiceImpl implements RoomfeedService {
   
   @Autowired
   private ModelMapper modelMapper; 
   
   @Autowired
   private RoomfeedRepository rfrepository;
   
   private String dir = "C:/resources/upload/";

   @Override
   public void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception {
      Date today = new Date(System.currentTimeMillis());
        roomfeedDto.setRoomCreateDate(today);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      String memberId = authentication.getName();
      String filenames = "";
		if (files != null && files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && !files[i].isEmpty()) {
					String fileName = files[i].getOriginalFilename();
					System.out.println(fileName);
					File dfile = new File(dir + fileName);
					files[i].transferTo(dfile);
//					filenames += files[i].getOriginalFilename()+",";
				}
			}
		}
		
      RoomfeedEntity roomfeed = modelMapper.map(roomfeedDto, RoomfeedEntity.class);
//      roomfeed.setFilename(filenames);
      rfrepository.save(roomfeed);
   }

   @Override
   public List<RoomfeedEntity> selectFeed(Long roomid) throws Exception {
      return rfrepository.findAllByRoomByRoomID(roomid);
   }

	@Override
	public Optional<RoomfeedEntity> detailFeed(Long feedId) throws Exception {
		return rfrepository.findById(feedId);
	}
}


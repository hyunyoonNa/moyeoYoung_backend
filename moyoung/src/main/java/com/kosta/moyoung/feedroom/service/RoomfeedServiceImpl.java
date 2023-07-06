package com.kosta.moyoung.feedroom.service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.PhotoEntity;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.feedroom.repository.PhotoRepository;
import com.kosta.moyoung.feedroom.repository.RoomfeedRepository;
@Service
public class RoomfeedServiceImpl implements RoomfeedService {
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private RoomfeedRepository rfrepository;
	
	@Autowired
	private PhotoRepository phororepository;
	
	private String dir = "C:/resources/upload/";

	@Override
	public void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception {
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(today);
        roomfeedDto.setRoomCreateDate(today);
		
		for (int i = 0; i < files.length; i++) {  
			if (files[i] != null && !files[i] .isEmpty()) { 
				String fileName = files[i].getOriginalFilename();
				System.out.println(fileName);
				File dfile = new File(dir + fileName);
				files[i].transferTo(dfile);
				
			}  
		}
		RoomfeedEntity roomfeed = modelMapper.map(roomfeedDto, RoomfeedEntity.class);
		rfrepository.save(roomfeed);
		PhotoEntity photo = modelMapper.map(roomfeedDto, PhotoEntity.class);
		photo.setRoomfeedentity(roomfeed);
		phororepository.save(photo);


	}

}



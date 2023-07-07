package com.kosta.moyoung.feedroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.service.RoomfeedService;

@RestController
@RequestMapping("feed")
public class RoomFeedController {
	
	@Autowired
	private RoomfeedService roomfeedservice;
	
	@PostMapping("/writefeed/{roomId}")
	public ResponseEntity<String> writefeed(@PathVariable Long roomId, @ModelAttribute RoomFeedDTO roomfeedDto,
		@RequestParam(value = "files", required=false) MultipartFile[] files){
		System.out.println(roomId);
	
		String FileNames ="";
		for (MultipartFile mf : files) {
			String originFileName = mf.getOriginalFilename(); // 원본 파일 명
			long fileSize = mf.getSize(); // 파일 사이즈
			System.out.println("originFileName : " + originFileName);
			System.out.println("fileSize : " + fileSize);
			String safeFile =System.currentTimeMillis() + originFileName;
			FileNames = FileNames+","+safeFile; 
		}
		try {
			roomfeedservice.WriteFeed(roomfeedDto, files);
			return new ResponseEntity<String>("피드작성완료", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}

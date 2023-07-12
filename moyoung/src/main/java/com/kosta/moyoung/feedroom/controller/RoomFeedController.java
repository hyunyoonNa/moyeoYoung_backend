package com.kosta.moyoung.feedroom.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.feedroom.service.RoomfeedService;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.security.jwt.JwtUtil;

@RestController
@RequestMapping("feed")
public class RoomFeedController {
   
   @Autowired
   private RoomfeedService roomfeedservice;
   
   @Autowired
   private MemberService memberService;
   
   //피드 작성
   @PostMapping("/writefeed/{roomId}")
   public ResponseEntity<String> writefeed(@PathVariable Long roomId, @ModelAttribute RoomFeedDTO roomfeedDto,
      @RequestParam(value = "files", required=false) MultipartFile[] files){
      try {
         roomfeedservice.WriteFeed(roomfeedDto, files);
         return new ResponseEntity<String>("피드작성완료", HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
      }
   }
   
   // 피드 조회
   @GetMapping("/selectfeed/{roomId}")
   public ResponseEntity<List<RoomFeedDTO>> selectfeed(@PathVariable Long roomId){
      try {
         return new ResponseEntity<List<RoomFeedDTO>>(roomfeedservice.selectFeed(roomId), HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         return new ResponseEntity<List<RoomFeedDTO>>(HttpStatus.BAD_REQUEST);
      }
   }
   
   // 피드 상세 조회
   @GetMapping("/detailfeed/{feedId}")
   public ResponseEntity<RoomFeedDTO> detailfeed(@PathVariable Long feedId){
	   try {
		   return new ResponseEntity<RoomFeedDTO>(roomfeedservice.detailFeed(feedId), HttpStatus.OK);
	   }catch (Exception e) {
		   e.printStackTrace();
		   return new ResponseEntity<RoomFeedDTO>(HttpStatus.BAD_REQUEST);
	   }
   }
   
   // 피드 수정
   @PostMapping("/modifyfeed/{feedId}")
   public ResponseEntity<String> modifyfeed(@PathVariable Long feedId, @ModelAttribute RoomFeedDTO roomfeedDto) {
		try {
			roomfeedservice.modifyFeed(feedId, roomfeedDto);
			return new ResponseEntity<String>("수정완료", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
   }
   
   // 피드 삭제 
   @PostMapping("/deletefeed/{feedId}")
   public void deletefeed(@PathVariable Long feedId) {
		try {
			roomfeedservice.deletefeed(feedId);
		} catch (Exception e) {
			e.printStackTrace();
		}
   }

   
   @GetMapping("/feedimg/{imgName}")
	public void image(@PathVariable("imgName") String imgName, HttpServletResponse response) {
		try {
			roomfeedservice.feedFileView(imgName, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
   
}
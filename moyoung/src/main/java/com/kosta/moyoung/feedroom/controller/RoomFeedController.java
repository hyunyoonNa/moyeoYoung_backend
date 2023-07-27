package com.kosta.moyoung.feedroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.kosta.moyoung.feedroom.dto.CommentDTO;
import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.LikeEntity;
import com.kosta.moyoung.feedroom.service.CommentService;
import com.kosta.moyoung.feedroom.service.RoomfeedService;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.notification.entity.NotificationType;
import com.kosta.moyoung.notification.service.NotificationService;
import com.kosta.moyoung.security.jwt.JwtUtil;

@RestController
@RequestMapping("feed")
public class RoomFeedController {
   
   @Autowired
   private RoomfeedService roomfeedservice;
   
   @Autowired
   private MemberService memberService;
   
   @Autowired
   private CommentService commentService;
   
   
  
   
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
		   return new ResponseEntity<RoomFeedDTO>(HttpStatus.BAD_REQUEST);
	   }
   }
   
   // 피드 수정
   @PostMapping("/modifyfeed/{feedId}")
   public ResponseEntity<String> modifyfeed(@PathVariable Long feedId, @ModelAttribute RoomFeedDTO roomfeedDto) {
	   System.out.println(roomfeedDto.getContent());
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
   
   //피드 좋아요
   @GetMapping("/like/{feedId}")
   public ResponseEntity<String> likefeed(@PathVariable("feedId") Long feedId){
	   try {
		   Long memberId = JwtUtil.getCurrentMemberId();
		   System.out.println(memberId);
		   roomfeedservice.increaseLike(feedId, memberId);
		
		   return new ResponseEntity<String>("좋아요", HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}
   }
 //피드 좋아요취소
   @GetMapping("delike/{feedId}")
   public ResponseEntity<String> Delikefeed(@PathVariable("feedId") Long feedId){
	   try {
		   Long memberId = JwtUtil.getCurrentMemberId();
		   roomfeedservice.decreaseLike(feedId, memberId);
		   return new ResponseEntity<String>("좋아요취소", HttpStatus.OK);
		} catch (Exception e) {
		   return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
   }
   //좋아요 리스트
   @GetMapping("likelist")
   public ResponseEntity<List<Long>> Likelist(){
	   try {
		   Long memberId = JwtUtil.getCurrentMemberId();
		   System.out.println(memberId);
		   return new ResponseEntity<List<Long>>(roomfeedservice.isLike(memberId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Long>>(HttpStatus.BAD_REQUEST);
		}
   }
   
   //댓글 작성
   @PostMapping("writecomment/{feedId}")
   public ResponseEntity<String> writecomment(@PathVariable("feedId") Long feedId, @ModelAttribute CommentDTO commentDto){
	   try {
		   commentService.WriteComment(commentDto,feedId);
		   return new ResponseEntity<String>("댓글 작성 완료", HttpStatus.OK);
	   }catch (Exception e) {
		   e.printStackTrace();
		   return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	   }
   }
   
   //댓글 조회
   @GetMapping("selectcomment/{feedId}")
   public ResponseEntity<List<CommentDTO>> selectcomment(@PathVariable("feedId") Long feedId){
	    try {
	    	commentService.selectComment(feedId);
	    	return new ResponseEntity<List<CommentDTO>>(commentService.selectComment(feedId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CommentDTO>>(HttpStatus.BAD_REQUEST);
		}
   }
   
   //댓글 삭제
   @PostMapping("deletecomment/{commentId}")
   public ResponseEntity<String> deletecomment(@PathVariable("commentId") Long commentId){
	   	try {	
	   		commentService.deleteComment(commentId);
	   		return new ResponseEntity<String>("댓글 삭제 완료", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
   }
   
   // 마이페이지 피드 조회
   @GetMapping("selectfeeds/{memberId}")
   public ResponseEntity<List<RoomFeedDTO>> selectByMemberId(@PathVariable("memberId") Long memberId) {
	   try {
		   return new ResponseEntity<List<RoomFeedDTO>> (roomfeedservice.selectFeeds(memberId), HttpStatus.OK);
	   } catch (Exception e) {
		   e.printStackTrace();
		   return new ResponseEntity<List<RoomFeedDTO>>(HttpStatus.BAD_REQUEST);
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
   

//   
   @GetMapping("getmemberId")
   public Long getmemberId() {
	  return JwtUtil.getCurrentMemberId();
   }
   
   
}
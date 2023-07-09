package com.kosta.moyoung.openroom.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import java.util.Optional;
import javax.websocket.server.PathParam;  
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.service.OpenRoomService;
import com.kosta.moyoung.security.jwt.JwtUtil;
import com.kosta.moyoung.util.PageInfo;
 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestController  
@RequestMapping("room") 
public class OpenRoomController {
	@Autowired
	private OpenRoomService orService;

	@PostMapping("/makeRoom") 
	public ResponseEntity<Long> makeRoom(@ModelAttribute RoomDTO roomDto, 
			@RequestParam(value = "file", required=false) MultipartFile file){ 
		try {
			Long roomId = orService.makeRoom(roomDto,file); 
			return new ResponseEntity<Long>(roomId ,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/getroomMain/{roomId}")
	public ResponseEntity<Room> selectRoomById(@PathVariable Long roomId){
		System.out.println(roomId);
		try {
			Room room = orService.selectById(roomId);
			System.out.println(room);
			return new ResponseEntity<Room>(room, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<Room>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/roomList/{page}")
	public ResponseEntity<Map<String, Object>> roomList(@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			List<RoomDTO> list = orService.findRoomList(page, pageInfo);
			Map<String,Object> res = new HashMap<>();  
			res.put("pageInfo", pageInfo);
			res.put("list", list); 
      
			Long memberId = Long.valueOf(1);;
			List<Long> isBookmarks = orService.isBookmarks(memberId); 
			if(!isBookmarks.isEmpty()) {
				res.put("isBookmarks", isBookmarks); 				
			}
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	} 
  
	@GetMapping("/roomListByCate/{page}")
	public ResponseEntity<Map<String, Object>> roomListByCate(@RequestParam("cateName") String cateName,
			@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			List<RoomDTO> list = orService.fineRoomByCategory(cateName, page, pageInfo);
			res.put("list", list);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/roomListByWord/{page}")
	public ResponseEntity<Map<String, Object>> roomListByWord(@RequestParam("word") String word,
			@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			List<RoomDTO> list = orService.fineRoomByWord(word, page, pageInfo);
			res.put("list", list);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/view/{imgName}")
	public void image(@PathVariable("imgName") String imgName, HttpServletResponse response) {
		try {
			System.out.println(imgName);
			orService.fileView(imgName, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/bookmark/{roomId}")
	public ResponseEntity<Map<String,Object>> bookmark(@PathVariable("roomId") Long roomId) {
		try {
			//유저id
			Boolean isBookmark = orService.bookMark(roomId,Long.valueOf(1)); 
			Map<String,Object> map = new HashMap<>();
			map.put("isBookmark", isBookmark);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK); 
      
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	 

//	@GetMapping("/test")
//	public ResponseEntity<Map<String,Object>> bookmark() {
//		Map<String,Object> map = new HashMap<>(); 
//		try {   
//		    map.put("test",JwtUtil.getCurrentMemberId());
//			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
//		}
//	}
}

package com.kosta.moyoung.openroom.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.service.OpenRoomService;
import com.kosta.moyoung.util.PageInfo;

@RestController
public class OpenRoomController {
	@Autowired
	private OpenRoomService orService;

	@PostMapping("/makeRoom")
	public ResponseEntity<String> makeRoom(@ModelAttribute RoomDTO roomDto,
			@RequestParam(value = "file", required = false) MultipartFile file) {

//		System.out.println(roomDto);
		try {
			orService.makeRoom(roomDto, file);
			return new ResponseEntity<String>("모임방 개설 성공!", HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("모임방 개설 실패 ㅠ.ㅠ", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/roomList/{page}")
	public ResponseEntity<Map<String,Object>> roomList(@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			List<RoomDTO> list = orService.findRoomList(page, pageInfo);
			Map<String,Object> res = new HashMap<>();
			res.put("pageInfo", pageInfo);
			res.put("list", list);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	//모임방리스트
//	@GetMapping("/roomList")
//	public ResponseEntity<List<Room>> roomList() {
//		try {
//			List<Room> list = orService.findRoomList();
//			return new ResponseEntity<List<Room>>(list, HttpStatus.OK);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<List<Room>>(HttpStatus.BAD_REQUEST);
//		}
//	}

	@GetMapping("/roomListByCate/{page}") 
	public ResponseEntity<Map<String,Object>> roomListByCate(@RequestParam("cateName") String cateName, @PathVariable Integer page) {
		try { 
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			List<RoomDTO> list = orService.fineRoomByCategory(cateName, page, pageInfo);
			res.put("list",list);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/roomListByWord/{page}")
	public ResponseEntity<Map<String,Object>> roomListByWord(@RequestParam("word") String word, @PathVariable Integer page) {
		try { 
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			List<RoomDTO> list = orService.fineRoomByWord(word,page,pageInfo);
			res.put("list", list);
			res.put("pageInfo", pageInfo); 
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/view/{imgName}")
	public void image(@PathVariable("imgName") String imgName, HttpServletResponse response) {
		try {
			orService.fileView(imgName, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

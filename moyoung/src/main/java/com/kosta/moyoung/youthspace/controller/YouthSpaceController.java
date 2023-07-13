package com.kosta.moyoung.youthspace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.util.PageInfo;
import com.kosta.moyoung.youthspace.dto.YouthSpaceDTO;
import com.kosta.moyoung.youthspace.service.YouthSpaceService;

@RestController
@RequestMapping("/youth")
public class YouthSpaceController {
	@Autowired
	private YouthSpaceService ysService;
	
	@GetMapping("/allYouthSpaceList/{page}")
	public ResponseEntity<Map<String,Object>> getAllYouthSpaceList(@PathVariable("page") Integer page, @RequestParam("cnt") Integer cnt){
		try { 
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			res.put("list", ysService.getAllYouthSpaceList(page, pageInfo, cnt));
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK); 
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	}
	@GetMapping("/youthSpaceDetail/{spaceId}")
	public ResponseEntity<Map<String,Object>> getYouthSpace(@PathVariable Integer spaceId){
		try {
			Map<String, Object> res = new HashMap<>();
			YouthSpaceDTO spaceDto = ysService.getYouthSpace(spaceId);
			System.out.println(spaceDto);
			res.put("space", spaceDto);
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK); 
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	}
	
	@GetMapping("/searchSpaceListByWord/{page}")
	public ResponseEntity<Map<String,Object>> searchSpaceListByWord(@PathVariable Integer page, @RequestParam("word") String word){
		try {
			Map<String, Object> res = new HashMap<>();
			PageInfo pageInfo = new PageInfo();
			List<YouthSpaceDTO> spaceDtoList = ysService.searchSpaceListByWord(page,pageInfo, word );
			res.put("list", spaceDtoList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK); 
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	}
	
	//장소로 검색
	@GetMapping("/searchSpaceListByLoc/{page}")
	public ResponseEntity<Map<String,Object>> searchSpaceListByLoc(@PathVariable Integer page, @RequestParam("loc") String loc,@RequestParam(value = "place", required=false) String place ){
		try {
			Map<String, Object> res = new HashMap<>();
			PageInfo pageInfo = new PageInfo();
			List<YouthSpaceDTO> spaceDtoList = ysService.searchSpaceListByLoc(page,pageInfo, loc, place);
			res.put("list", spaceDtoList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK); 
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	} 
	
	

}

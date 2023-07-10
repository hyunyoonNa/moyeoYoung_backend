package com.kosta.moyoung.youthspace.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Map<String,Object>> getAllYouthSpaceList(@PathVariable("page") Integer page){
		try { 
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			res.put("list", ysService.getAllYouthSpaceList(page, pageInfo));
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

}

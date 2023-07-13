package com.kosta.moyoung.youthspace.service;

import java.util.List;

import com.kosta.moyoung.util.PageInfo;
import com.kosta.moyoung.youthspace.dto.YouthSpaceDTO;

public interface YouthSpaceService {
	List<YouthSpaceDTO> getAllYouthSpaceList(Integer page, PageInfo pageInfo, Integer cnt)throws Exception;
	YouthSpaceDTO getYouthSpace(Integer spaceId)throws Exception;  
	List<YouthSpaceDTO> searchSpaceListByWord(Integer page, PageInfo pageInfo,String word)throws Exception;
	List<YouthSpaceDTO> searchSpaceListByLoc(Integer page, PageInfo pageInfo,String loc, String place)throws Exception;
	
	
}

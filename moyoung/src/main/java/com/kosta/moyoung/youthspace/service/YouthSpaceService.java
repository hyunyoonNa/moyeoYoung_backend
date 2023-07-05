package com.kosta.moyoung.youthspace.service;

import java.util.List;

import com.kosta.moyoung.util.PageInfo;
import com.kosta.moyoung.youthspace.dto.YouthSpaceDTO;

public interface YouthSpaceService {
	List<YouthSpaceDTO> getAllYouthSpaceList(Integer page, PageInfo pageInfo)throws Exception;
	YouthSpaceDTO getYouthSpace(Integer spaceId)throws Exception;  
}

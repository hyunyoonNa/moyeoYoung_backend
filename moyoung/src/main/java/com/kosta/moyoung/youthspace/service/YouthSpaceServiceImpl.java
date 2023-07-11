package com.kosta.moyoung.youthspace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.util.PageInfo;
import com.kosta.moyoung.youthspace.dto.YouthSpaceDTO;
import com.kosta.moyoung.youthspace.entity.YouthSpace;
import com.kosta.moyoung.youthspace.repository.YouthSpaceRepository;

@Service
public class YouthSpaceServiceImpl implements YouthSpaceService {

	@Autowired
	private YouthSpaceRepository ysRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<YouthSpaceDTO> getAllYouthSpaceList(Integer page, PageInfo pageInfo) throws Exception {

		PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "spaceId"));
		Page<YouthSpace> ysList = ysRepository.findAll(pageRequest);

		pageInfo.setAllPage(ysList.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 10 - 1;
		if (endPage > pageInfo.getAllPage())
			endPage = pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		List<YouthSpaceDTO> ysDtoList = new ArrayList<>();
		System.out.println(ysList);
		for (YouthSpace ys : ysList) {
			YouthSpaceDTO ysDto = modelMapper.map(ys, YouthSpaceDTO.class);
			ysDto.setImgURLs(ysDto.getImgs().split(","));

			String placeLoc = ys.getPlace().substring(0, (ys.getPlace().indexOf("구") + 1));
			ysDto.setPlaceLoc(placeLoc);
			ysDtoList.add(ysDto);
		}
		return ysDtoList;
	}

	@Override
	public YouthSpaceDTO getYouthSpace(Integer spaceId) throws Exception {
		Optional<YouthSpace> space = ysRepository.findById(spaceId);
		if (space.isEmpty()) {
			throw new Exception("청년공간 정보가 존재하지 않습니다");
		} else {
			YouthSpaceDTO spaceDto = modelMapper.map(space.get(), YouthSpaceDTO.class);
			if (spaceDto.getImgs().contains(",") == true) {
				spaceDto.setImgURLs(spaceDto.getImgs().split(","));
			} else {
				System.out.println("없나봐");
				String[] sarr = new String[1];
				sarr[0] = spaceDto.getImgs();
				spaceDto.setImgURLs(sarr);
			}
			return spaceDto;
		}

	}

	@Override
	public List<YouthSpaceDTO> searchSpaceListByWord(Integer page, PageInfo pageInfo, String word) throws Exception {
		PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "space_id"));
		Page<YouthSpace> ysList = ysRepository.findAllBySpaceWord(word, pageRequest);

		pageInfo.setAllPage(ysList.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 10 - 1;
		if (endPage > pageInfo.getAllPage())
			endPage = pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		List<YouthSpaceDTO> ysDtoList = new ArrayList<>();
		System.out.println(ysList);
		for (YouthSpace ys : ysList) {
			YouthSpaceDTO ysDto = modelMapper.map(ys, YouthSpaceDTO.class);
			ysDto.setImgURLs(ysDto.getImgs().split(","));

			String placeLoc = ys.getPlace().substring(0, (ys.getPlace().indexOf("구") + 1));
			ysDto.setPlaceLoc(placeLoc);
			ysDtoList.add(ysDto);
		}
		return ysDtoList;
	}

	@Override
	public List<YouthSpaceDTO> searchSpaceListByLoc(Integer page, PageInfo pageInfo, String loc, String place)
			throws Exception {
		PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, "space_id"));
		Page<YouthSpace> ysList = null;
		System.out.println("loc:" + loc);
		System.out.println("place:" + place);
		if (loc.equals("전체")) {
			ysList = ysRepository.findAll(pageRequest);
		} else if (!loc.equals("전체") && place.equals("전체")) {
			ysList = ysRepository.findAllByLoc(loc, pageRequest);
		} else {
			ysList = ysRepository.findAllByLocAndPlace(loc, place, pageRequest);
		}

		pageInfo.setAllPage(ysList.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 10 - 1;
		if (endPage > pageInfo.getAllPage())
			endPage = pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		List<YouthSpaceDTO> ysDtoList = new ArrayList<>();
		System.out.println(ysList);
		for (YouthSpace ys : ysList) {
			YouthSpaceDTO ysDto = modelMapper.map(ys, YouthSpaceDTO.class);
			ysDto.setImgURLs(ysDto.getImgs().split(","));

			String placeLoc = ys.getPlace().substring(0, (ys.getPlace().indexOf("구") + 1));
			ysDto.setPlaceLoc(placeLoc);
			ysDtoList.add(ysDto);
		}
		return ysDtoList; 
	}

}

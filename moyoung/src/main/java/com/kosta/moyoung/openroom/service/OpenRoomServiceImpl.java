package com.kosta.moyoung.openroom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Optional;
import javax.servlet.ServletContext;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;

@Service
public class OpenRoomServiceImpl implements OpenRoomService {
	
//	@Autowired
//	private ServletContext servletContext;
	@Autowired
	private ModelMapper modelMapper;  
	@Autowired
	private OpenRoomRepository orRepository;
	
	private String dir = "C:/resources/upload/";

	@Override
	public Long makeRoom(RoomDTO roomDto, MultipartFile file) throws Exception {

		//1.개설일 설정
		Date today = new Date(System.currentTimeMillis()); 
		roomDto.setRoomCreateDate(today);
		//2. 유저id 설정
		roomDto.setUserId((long)101);
		//3. 멤버수 설정
		roomDto.setRoomUserCnt((long)1); 
		// 파일입력
		if (file != null && !file.isEmpty()) { 
			String fileName = file.getOriginalFilename();
			File dfile = new File(dir + fileName);
			file.transferTo(dfile);
		}  
		// save
		Room room = modelMapper.map(roomDto, Room.class); 
		orRepository.save(room);  
		return room.getRoomId();
	}

	@Override
	public Room selectById(Long id) throws Exception {
		Optional<Room> oroom = orRepository.findById(id);
		if(oroom.isPresent()) {
			return oroom.get();
		}
		return null;
	}

	@Override
	public List<Room> findRoomList() throws Exception {
		return orRepository.findAll();
	}

	@Override
	public List<Room> fineRoomByCategory(String cateName) throws Exception {
		return orRepository.findAllByRoomCategory(cateName);
	}

	@Override
	public void fileView(String imgName,OutputStream out) throws Exception { 
			FileInputStream fis = new FileInputStream(dir + imgName);
			FileCopyUtils.copy(fis, out);
			out.flush();  
	}

	@Override
	public List<Room> fineRoomByWord(String word) throws Exception { 
		return orRepository.findAllByRoomWord(word);
	}
	

}

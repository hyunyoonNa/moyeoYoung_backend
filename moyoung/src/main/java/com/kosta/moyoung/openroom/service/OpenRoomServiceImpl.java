package com.kosta.moyoung.openroom.service; 
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Bookmark;
//import com.kosta.moyoung.openroom.entity.Bookmark;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.BookmarkRepository;
//import com.kosta.moyoung.openroom.repository.BookmarkRepository;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;
import com.kosta.moyoung.util.FileService;
import com.kosta.moyoung.util.PageInfo;

@Service
public class OpenRoomServiceImpl implements OpenRoomService {
	 
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MemberRepository memberRepository; 
	@Autowired
	private OpenRoomRepository orRepository;
	@Autowired
	private BookmarkRepository bookmarkRepository;
	@Autowired
	private FileService fileService; 
	
	@Autowired
	private MemberService memberService;
	

	@Override
	public Long makeRoom(RoomDTO roomDto, MultipartFile file,Member mem) throws Exception { 
		//1.개설일 설정
		Date today = new Date(System.currentTimeMillis()); 
		roomDto.setRoomCreateDate(today);  
		//3. 멤버수 설정
		roomDto.setRoomUserCnt((long)1); 
		// 파일입력
		fileService.fileUpload(file);
		// save 
		Room room = new Room(roomDto, mem);
		orRepository.save(room);  
		return room.getRoomId();
	}
	
	@Override
	public RoomDTO selectById(Long id) throws Exception { 
		Optional<Room> oroom = orRepository.findById(id); 
		if(oroom.isEmpty()) {
			return null;
		} 
		return new RoomDTO(oroom.get()); 
	}
	 
	@Override
	public List<RoomDTO> findRoomList(Integer page, PageInfo pageInfo, Integer cnt) throws Exception {
		
		Integer reqCnt = 8;
		
		if(cnt!=null && cnt>=1) {
			reqCnt = cnt;
		}
		PageRequest pageRequest = PageRequest.of(page - 1, reqCnt, Sort.by(Sort.Direction.DESC, "roomId")); 			
		Page<Room> rooms = orRepository.findAll(pageRequest);
		
		pageInfo.setAllPage(rooms.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page-1)/8*8+1; 
		int endPage = startPage+8-1;
		if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage); 
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room r : rooms.getContent()) {
			list.add(new RoomDTO(r));
		}
		return list; 
	}

	@Override
	public List<RoomDTO> fineRoomByCategory(String cateName,Integer page, PageInfo pageInfo) throws Exception {
		PageRequest pageRequest = PageRequest.of(page-1,8,Sort.by(Sort.Direction.DESC, "room_id"));
		Page<Room> rooms = orRepository.findAllByRoomCategory(cateName, pageRequest);
		pageInfo.setAllPage(rooms.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page-1)/8*8+1; 
		int endPage = startPage+8-1;
		if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room r : rooms.getContent()) {
			list.add(new RoomDTO(r));
		}
		return list; 
	}

	
	@Override
	public List<RoomDTO> fineRoomByWord(String word, Integer page, PageInfo pageInfo) throws Exception {  
		PageRequest pageRequest = PageRequest.of(page-1,8,Sort.by(Sort.Direction.DESC, "room_id"));
		Page<Room> rooms = orRepository.findAllByRoomWord(word, pageRequest);
		pageInfo.setAllPage(rooms.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page-1)/8*8+1; 
		int endPage = startPage+8-1;
		if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room r : rooms.getContent()) {
			list.add(new RoomDTO(r));
		}
		return list; 
	}
	

	@Override
	public Boolean bookMark(Long roomId, Long memberId) throws Exception {  
		Optional<Room> oroom = orRepository.findById(roomId);
		Optional<Member> omember = memberRepository.findById(memberId); 
		//유저id,방id로 북마크 찾기
		Bookmark bookmark = null;
		if(omember.isPresent()) {
			Member member = omember.get();
			List<Bookmark> bookmarks = member.getBookmarks();
			for(Bookmark b : bookmarks) {
				if(b.getRoomBookmark().getRoomId()==roomId) {
					bookmark = b;
					break;
				}
			}
		}
		
		if(oroom.isPresent()) {
			if(bookmark!=null) {
				bookmarkRepository.delete(bookmark);
				return false;
			}else {
				Bookmark mark = new Bookmark(omember.get(), oroom.get()); 
				bookmarkRepository.save(mark);
				return true;
			}
		}else {
			throw new Exception("모임방이 존재하지 않음!!!");
		}
		 
	}

	@Override
	@Transactional
	public List<Long> isBookmarks(Long memberId) throws Exception {
		List<Long> list = new ArrayList<>();
		Optional<Member> omember = memberRepository.findById(memberId);
		if(omember.isPresent()) {
			Member member = omember.get();
			List<Bookmark> bookmarks = member.getBookmarks();
			for(Bookmark b : bookmarks) {
				list.add(b.getRoomBookmark().getRoomId());
			}
		}
		return list;
	}

	
	
	 
	

}

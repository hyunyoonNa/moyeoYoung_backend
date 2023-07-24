package com.kosta.moyoung.feedroom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.feedroom.dto.LikeDTO;
import com.kosta.moyoung.feedroom.dto.RoomFeedDTO;
import com.kosta.moyoung.feedroom.entity.LikeEntity;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.feedroom.repository.LikeRepository;
import com.kosta.moyoung.feedroom.repository.RoomfeedRepository;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;
@Service
public class RoomfeedServiceImpl implements RoomfeedService {
   
   @Autowired
   private ModelMapper modelMapper; 
   
   @Autowired
   private RoomfeedRepository rfrepository;
	
   @Autowired
   private MemberRepository memberRepository; 
   
   @Autowired
   private OpenRoomRepository oprepository;
   
   @Autowired
   private LikeRepository likeRepo;
   
   private String dir = "C:/resources/upload/";

   @Override
   public void WriteFeed(RoomFeedDTO roomfeedDto, MultipartFile[] files) throws Exception {
      Date today = new Date(System.currentTimeMillis());
      Long memberId = JwtUtil.getCurrentMemberId();
      roomfeedDto.setRoomCreateDate(today);
      roomfeedDto.setMemberId(memberId);
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String filenames = "";
		if (files != null && files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] != null && !files[i].isEmpty()) {
					String fileName = files[i].getOriginalFilename();
					File dfile = new File(dir + fileName);
					files[i].transferTo(dfile);
				}
			}
		}
      RoomfeedEntity roomfeed = modelMapper.map(roomfeedDto, RoomfeedEntity.class);
      rfrepository.save(roomfeed);
   }

   @Override
   public List<RoomFeedDTO> selectFeed(Long roomId) throws Exception {
	   Optional<Room> room = oprepository.findById(roomId);
	   List<RoomfeedEntity> Rfeeds = room.get().getRoomfeeds();
	   
	   Rfeeds.sort(Comparator.comparing(RoomfeedEntity::getFeedId).reversed()); // 최신순으로
	   
	   List<RoomFeedDTO> feeds = new ArrayList<>();
	   for (RoomfeedEntity roomFeedEntity : Rfeeds) {
	        RoomFeedDTO roomFeedDTO = new RoomFeedDTO();
	        roomFeedDTO.setTitle(roomFeedEntity.getTitle());
	        roomFeedDTO.setMemberId(roomFeedEntity.getMember().getMemberId());
	        roomFeedDTO.setNickname(roomFeedEntity.getMember().getNickname());
	        roomFeedDTO.setProfilename(roomFeedEntity.getMember().getFileName());
	        roomFeedDTO.setContent(roomFeedEntity.getContent());
	        roomFeedDTO.setFeedId(roomFeedEntity.getFeedId());
	        roomFeedDTO.setFilename(roomFeedEntity.getFilename());
	        roomFeedDTO.setRoomCreateDate(roomFeedEntity.getRoomCreateDate());
	        roomFeedDTO.setLikeCount(roomFeedEntity.getLikes().size());
	        roomFeedDTO.setCommentCount(roomFeedEntity.getComments().size());
	        feeds.add(roomFeedDTO);
	   }
	   return feeds;
   }

	@Override
	public RoomFeedDTO detailFeed(Long feedId) throws Exception {
		Optional<RoomfeedEntity> rfeed = (rfrepository.findById(feedId));
		RoomFeedDTO feed = new RoomFeedDTO();
		feed.setContent(rfeed.get().getContent());
		feed.setFilename(rfeed.get().getFilename());
		feed.setTitle(rfeed.get().getTitle());
		feed.setFeedId(rfeed.get().getFeedId());
		return feed;
	}
	
	@Override 
	public void feedFileView(String imgName,OutputStream out) throws Exception { 
		FileInputStream fis = new FileInputStream(dir + imgName);
		FileCopyUtils.copy(fis, out);
		out.flush();  
	}
	
	@Override
	public void deletefeed(Long feedId) throws Exception {
		rfrepository.deleteById(feedId);
	}

	@Override
	public void modifyFeed(Long feedId, RoomFeedDTO roomfeedDto) throws Exception {
		Optional<RoomfeedEntity> ofeed = rfrepository.findById(feedId);
		RoomfeedEntity feed = ofeed.get();
		feed.setContent(roomfeedDto.getContent());
		feed.setTitle(roomfeedDto.getTitle());
		feed.setFilename(roomfeedDto.getFilename());
		rfrepository.save(feed);
	}

	@Override
	public void increaseLike(Long feedId, Long memberId) throws Exception {
		LikeDTO like = new LikeDTO();
		like.setFeedId(feedId);
		like.setMemberId(memberId);
		LikeEntity likes = modelMapper.map(like, LikeEntity.class);
		likeRepo.save(likes);	
	}
	
	@Override
	public void decreaseLike(Long feedId, Long memberId) throws Exception {
		likeRepo.deleteByFeedIdAndMemberId(feedId, memberId);
	}

	@Override
	public List<Long> isLike(Long memberId) throws Exception {
		List<Long> list = new ArrayList<>();
		Optional<Member> omember = memberRepository.findById(memberId);
		System.out.println(omember.get().getLike());
		if(omember.isPresent()) {
			Member member = omember.get();
			List<LikeEntity> likes = member.getLike();
			for(LikeEntity e : likes) {
				list.add(e.getFeed().getFeedId());
			}
		}
		return list;
	}

	@Override
	public List<RoomFeedDTO> selectFeeds(Long memberId) throws Exception {
		Optional<Member> member = memberRepository.findById(memberId);
		List<RoomFeedDTO> feeds = new ArrayList<>();
		int roomsize = member.get().getRooms().size();
		for(int i = 0; i<=roomsize-1; i++) {
			List<RoomfeedEntity> Rfeeds = member.get().getRooms().get(i).getRoomfeeds();
			for (RoomfeedEntity roomFeedEntity : Rfeeds) {
			        RoomFeedDTO roomFeedDTO = new RoomFeedDTO();
			        roomFeedDTO.setTitle(roomFeedEntity.getTitle());
			        roomFeedDTO.setMemberId(memberId);
			        roomFeedDTO.setRoomId(roomFeedEntity.getRoom().getRoomId());
			        roomFeedDTO.setNickname(roomFeedEntity.getMember().getNickname());
			        roomFeedDTO.setProfilename(roomFeedEntity.getMember().getFileName());
			        roomFeedDTO.setContent(roomFeedEntity.getContent());
			        roomFeedDTO.setFeedId(roomFeedEntity.getFeedId());
			        roomFeedDTO.setFilename(roomFeedEntity.getFilename());
			        roomFeedDTO.setRoomCreateDate(roomFeedEntity.getRoomCreateDate());
			        roomFeedDTO.setLikeCount(roomFeedEntity.getLikes().size());
			        roomFeedDTO.setCommentCount(roomFeedEntity.getComments().size());
			        feeds.add(roomFeedDTO);
			   }
		}
		return feeds;
	}

	
}


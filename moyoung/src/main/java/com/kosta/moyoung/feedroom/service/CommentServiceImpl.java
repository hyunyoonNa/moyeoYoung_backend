package com.kosta.moyoung.feedroom.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.feedroom.dto.CommentDTO;
import com.kosta.moyoung.feedroom.entity.CommentEntity;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.feedroom.repository.CommentRepository;
import com.kosta.moyoung.feedroom.repository.RoomfeedRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;

@Service
public class CommentServiceImpl implements CommentService {
	
    @Autowired
    private ModelMapper modelMapper; 
	
	@Autowired
	CommentRepository commentrepository;
	
	@Autowired
	RoomfeedRepository roomfeedRe;
	
	@Override
	public void WriteComment(CommentDTO commentDto, Long feedId) {
		Long memberId = JwtUtil.getCurrentMemberId();
		LocalDateTime today = LocalDateTime.now();
		commentDto.setMemberId(memberId);
		commentDto.setCommentCreateDate(today);
		commentDto.setFeedId(feedId);
		CommentEntity comment = modelMapper.map(commentDto, CommentEntity.class);
		commentrepository.save(comment);
	}

	@Override
	public List<CommentDTO> selectComment(Long feedId) {
		Optional<RoomfeedEntity> ofeed = roomfeedRe.findById(feedId);
		List<CommentEntity> ocomments = ofeed.get().getComments();
		
		List<CommentDTO> comments = new ArrayList<>();
		for(CommentEntity comment : ocomments) {
			CommentDTO commentDto = new CommentDTO();
			commentDto.setComment(comment.getComment());
			commentDto.setCommentCreateDate(comment.getCommentCreateDate());
			commentDto.setCommentId(comment.getCommentId());
			commentDto.setFeedId(feedId);
			commentDto.setMemberId(comment.getMember().getMemberId());
			commentDto.setNickname(comment.getMember().getNickname());
			commentDto.setProfilename(comment.getMember().getFileName());
			comments.add(commentDto);
		}
		return comments;
	}

	@Override
	public void deleteComment(Long commentId) {
		commentrepository.deleteById(commentId);
	}

}

package com.kosta.moyoung.feedroom.service;

import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.feedroom.dto.CommentDTO;
import com.kosta.moyoung.feedroom.entity.CommentEntity;
import com.kosta.moyoung.feedroom.repository.CommentRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;

@Service
public class CommentServiceImpl implements CommentService {
	
    @Autowired
    private ModelMapper modelMapper; 
	
	@Autowired
	CommentRepository commentrepository;
	
	@Override
	public void Writefeed(CommentDTO commentDto, Long feedId) {
		Long memberId = JwtUtil.getCurrentMemberId();
		Date today = new Date(System.currentTimeMillis());
		commentDto.setMemberId(memberId);
		commentDto.setCommentCreateDate(today);
		commentDto.setFeedId(feedId);
		CommentEntity comment = modelMapper.map(commentDto, CommentEntity.class);
		commentrepository.save(comment);
	}

}

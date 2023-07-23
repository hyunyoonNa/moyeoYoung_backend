package com.kosta.moyoung.feedroom.service;

import java.util.List;

import com.kosta.moyoung.feedroom.dto.CommentDTO;

public interface CommentService {
	void WriteComment(CommentDTO commentDto, Long feedId); 
	List<CommentDTO> selectComment(Long feedId);
	void deleteComment(Long commentId);
}

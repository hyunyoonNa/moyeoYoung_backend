package com.kosta.moyoung.feedroom.service;

import com.kosta.moyoung.feedroom.dto.CommentDTO;

public interface CommentService {
	void Writefeed(CommentDTO commentDto, Long feedId); 
}

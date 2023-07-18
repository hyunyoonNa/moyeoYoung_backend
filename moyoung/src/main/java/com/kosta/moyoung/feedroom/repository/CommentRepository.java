package com.kosta.moyoung.feedroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.moyoung.feedroom.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}

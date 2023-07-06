package com.kosta.moyoung.feedroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.moyoung.feedroom.entity.PhotoEntity;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

}

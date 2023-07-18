package com.kosta.moyoung.feedroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.moyoung.feedroom.entity.LikeEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from like_entity where feed_id = ?1 AND member_id = ?2", nativeQuery = true)
	void deleteByFeedIdAndMemberId(Long feedId, Long memberId);
}

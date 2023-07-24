package com.kosta.moyoung.openroom.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.entity.Bookmark; 

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	
	List<Bookmark> findByMemberBookmarkMemberId(Long memberId)throws Exception;
//	List<Bookmark> findByMemberMemberId(Long memberId)throws Exception;
//	List<Bookmark> findByMemberId(Long memberId)throws Exception;
//	List<Bookmark> findByMemberBookmark(Member member)throws Exception;
 
}

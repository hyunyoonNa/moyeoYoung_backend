package com.kosta.moyoung.note.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.note.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>{
	//member의 값과 일치하는 메시지들만 List로 반환
	List<Note> findAllByReceiver(Member member);
	List<Note> findAllBySender(Member member);
	Page<Note> findAllByReceiverOrderBySendDateDesc(Member member, PageRequest of);
    Page<Note> findAllBySenderOrderBySendDateDesc(Member sender, PageRequest of);
}
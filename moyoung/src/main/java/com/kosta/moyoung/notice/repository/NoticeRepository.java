package com.kosta.moyoung.notice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kosta.moyoung.notice.entity.Notice;

public interface NoticeRepository extends CrudRepository<Notice, Long> {
    List<Notice> findByRoomId(Long roomId);
    Optional<Notice> findByIdAndRoomId(Long id, Long roomId);

}

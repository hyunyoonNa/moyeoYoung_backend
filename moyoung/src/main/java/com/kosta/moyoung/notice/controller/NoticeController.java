package com.kosta.moyoung.notice.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.notice.entity.Notice;
import com.kosta.moyoung.notice.repository.NoticeRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 앱의 URL
public class NoticeController {
    private final NoticeRepository repository;

    NoticeController(NoticeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/rooms/{roomId}/notices")
    List<Notice> all(@PathVariable Long roomId) {
    	List<Notice> notices = repository.findByRoomId(roomId);
        Collections.reverse(notices);
        return notices;
    }

    @PostMapping("/rooms/{roomId}/notices")
    Notice newNotice(@PathVariable Long roomId, @RequestBody Notice newNotice) {
        newNotice.setRoomId(roomId);
        newNotice.setCreatedAt(LocalDateTime.now()); // 공지사항이 생성될 때마다 createdAt을 현재 시간으로 설정
        return repository.save(newNotice);
    }

    
    @GetMapping("/rooms/{roomId}/notices/{id}")
    Notice one(@PathVariable Long roomId, @PathVariable Long id) {
        return repository.findByIdAndRoomId(id, roomId)
            .orElseThrow(() -> new NoSuchElementException("Notice not found"));
    }

    @DeleteMapping("/rooms/{roomId}/notices/{id}")
    void deleteNotice(@PathVariable Long roomId, @PathVariable Long id) {
        Notice notice = repository.findByIdAndRoomId(id, roomId)
            .orElseThrow(() -> new NoSuchElementException("Notice not found"));
        repository.delete(notice);
    }

    @PutMapping("/rooms/{roomId}/notices/{id}")
    Notice replaceNotice(@PathVariable Long roomId, @RequestBody Notice newNotice, @PathVariable Long id) {
        return repository.findByIdAndRoomId(id, roomId)
            .map(notice -> {
                notice.setTitle(newNotice.getTitle());
                notice.setContent(newNotice.getContent());
                notice.setUpdatedAt(LocalDateTime.now()); // 공지사항이 업데이트될 때마다 updatedAt을 현재 시간으로 설정
                return repository.save(notice);
            })
            .orElseGet(() -> {
                newNotice.setId(id);
                newNotice.setRoomId(roomId);
                return repository.save(newNotice);
            });
    }



    // More methods for read, update, delete...
}
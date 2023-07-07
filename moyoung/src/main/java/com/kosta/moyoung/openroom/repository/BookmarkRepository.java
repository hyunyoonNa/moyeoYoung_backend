package com.kosta.moyoung.openroom.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 

import com.kosta.moyoung.openroom.entity.Bookmark; 

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
 
 
}

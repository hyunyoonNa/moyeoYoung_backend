package com.kosta.moyoung.youthspace.repository; 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kosta.moyoung.youthspace.entity.YouthSpace;

public interface YouthSpaceRepository extends JpaRepository<YouthSpace, Integer> {
	
	@Query(value="SELECT * FROM youth_space WHERE title LIKE CONCAT('%', ?1, '%')", nativeQuery = true) 
	Page<YouthSpace> findAllBySpaceWord(String word, PageRequest pageRequest)throws Exception; 
	
	@Query(value="select * from youth_space where place like CONCAT('%', ?1, '%')", nativeQuery = true) 
	Page<YouthSpace> findAllByLoc(String loc, PageRequest pageRequest)throws Exception; 
	
	@Query(value="select * from youth_space where place like  CONCAT('%', ?1, '%') and place like  CONCAT('%', ?2, '%')", nativeQuery = true) 
	Page<YouthSpace> findAllByLocAndPlace(String word,String place, PageRequest pageRequest)throws Exception; 
	
 
	
	

}

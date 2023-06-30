package com.kosta.moyoung;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;

@SpringBootTest
class MoyoungApplicationTests {

	@Autowired
	private OpenRoomRepository orRepository;
	
	@Test
	void contextLoads() {
	}
	
	// 방개설 save 테스트
//	@Test 
//	void makeRoom() {
//		try {
//			Room room =  Room.builder().roomId((long)1001).roomTitle("룰루랄라c방").roomContent("내가 왕이다!")
//					.roomCategory("프로젝트").roomType("open").userId((long)1).build();
//			orRepository.save(room);
//			System.out.println(room);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
}

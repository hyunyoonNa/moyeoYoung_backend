package com.kosta.moyoung.note.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.note.dto.NoteDto;
import com.kosta.moyoung.note.service.NoteService;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("note")
public class NoteController {
	private final MemberRepository  memberRepository;
//	private final MemberService memberService;
	private final NoteService noteService;
	
	@PostMapping("/send")
	public ResponseEntity<String> sendNote(@RequestBody NoteDto noteDto) {
		try {
			Optional<Member> member= memberRepository.findById(JwtUtil.getCurrentMemberId());
			if (member.isPresent()) {
			    noteDto.setSenderNickname(member.get().getNickname());
			    noteService.write(noteDto);
			    return new ResponseEntity<String>("쪽지 보내기 성공" + noteService.write(noteDto), HttpStatus.CREATED);
			} else {
			    return new ResponseEntity<String>("멤버를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<String>("쪽지 보내기 실패.."+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/received")
	public ResponseEntity<Map<String, Object>> receiveNote(){
		Map<String, Object> res = new HashMap<>();
		List<NoteDto> receiveList = null;
		try {
			Optional<Member> member= memberRepository.findById(JwtUtil.getCurrentMemberId());
			if(member.isEmpty()) {
				throw new Exception("아이디 오류");
			} else{ 
				receiveList = noteService.receiveNote(member.get());
				res.put("notes", receiveList);
			} 
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/received/{noteId}")
	public ResponseEntity<Boolean> deleteReceivedNote(@PathVariable Long noteId ) {
		System.out.println(noteId);
		try {
			Optional<Member> member= memberRepository.findById(JwtUtil.getCurrentMemberId());
			System.out.println("memberID: " + JwtUtil.getCurrentMemberId());
			if(member.isEmpty()) {
				throw new Exception("아이디 오류");
			} else{ 
				noteService.deleteNoteByReceiver(noteId, member.get());
				System.out.println("member.get()" + member.get());
				System.out.println(noteService.deleteNoteByReceiver(noteId, member.get()));
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} 
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/sent")
	public ResponseEntity<Map<String, Object>> sendNotes(){
		Map<String, Object> res = new HashMap<>();
		List<NoteDto> sentList = null;
		try {
			Optional<Member> member= memberRepository.findById(JwtUtil.getCurrentMemberId());
			if(member.isEmpty()) {
				throw new Exception("아이디 오류");
			} else{ 
				sentList = noteService.sentNote(member.get());
				res.put("notes", sentList);
			} 
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/sent/{noteId}")
	public ResponseEntity<Boolean> deleteSentNote(@PathVariable Long noteId ) {
		System.out.println(noteId);
		try {
			Optional<Member> member= memberRepository.findById(JwtUtil.getCurrentMemberId());
			System.out.println("memberID: " + JwtUtil.getCurrentMemberId());
			if(member.isEmpty()) {
				throw new Exception("아이디 오류");
			} else{ 
				noteService.deleteNoteBySender(noteId, member.get());
				System.out.println("member.get()" + member.get());
				System.out.println(noteService.deleteNoteBySender(noteId, member.get()));
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} 
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
	
}

package com.kosta.moyoung.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.note.dto.NoteDto;
import com.kosta.moyoung.note.entity.Note;
import com.kosta.moyoung.note.repository.NoteRepository;
import com.kosta.moyoung.notification.entity.NotificationType;
import com.kosta.moyoung.notification.repository.NotificationRepository;
import com.kosta.moyoung.notification.service.NotificationService;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
	
	private final NoteRepository noteRepository;
	private final MemberRepository memberRepository;
	private final NotificationService notificationService;
	
	@Transactional
	@Override
	public void write(NoteDto noteDto) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(noteDto.toString());
		Optional<Member> receiver  = memberRepository.findByNickname(noteDto.getReceiverNickname());
		Optional<Member> sender = memberRepository.findByNickname(noteDto.getSenderNickname());
		if (!receiver.isPresent() || !sender.isPresent()) {
		    throw new Exception("Receiver or sender not found");
		}
		if (noteDto.getReceiverNickname().equals(noteDto.getSenderNickname())) {
	        throw new Exception("자신에게는 쪽지를 보낼 수 없습니다.");
	    }
		
		Note note = Note.builder()
				.receiver(receiver.get())
				.sender(sender.get())
				.content(noteDto.getContent())
				.deletedByReceiver(false)
				.deletedBySender(false)
				.build();
			noteRepository.save(note);
			
			 // 알림 생성
	    notificationService.createNotification(sender.get(), receiver.get(), NotificationType.NEW_MESSAGE_ON_NOTE, "새로운 쪽지가 도착했습니다.", note.getNoteId());
	}
	
	
	@Transactional
	@Override
	public List<NoteDto> receiveNote(Member member) throws Exception {
		// TODO Auto-generated method stub
		 // 받은 편지함 불러오기
//		Optional<Member> omember= memberRepository.findById(JwtUtil.getCurrentMemberId());
//		Optional<Member> omember= memberRepository.findById(l);
//		System.out.println(omember.get().getMemberId());
//		if(omember.isEmpty()) {
//			throw new Exception("아이디 오류");
//		} 
		
		System.out.println(member.toString());
		List<Note> notes = noteRepository.findAllByReceiver(member);
		List<NoteDto> noteDto = new ArrayList<NoteDto>();
		
		for (Note note : notes) {
			if(!note.isDeletedByReceiver()) {
				noteDto.add(NoteDto.toNote(note));
			}
		}
		return noteDto;
	}

	@Override
	public Object deleteNoteByReceiver(Long noteId, Member member) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("noteId" + noteId);
		Note note = noteRepository.findById(noteId).orElseThrow(()-> {
			return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
		});
			System.out.println("member" + member);
			System.out.println("sender" + note.getSender());
			System.out.println("memberId " + member.getMemberId());
			Long memberId = JwtUtil.getCurrentMemberId();
			
//		if (memberId.equals(note.getSender().getMemberId())) {
		if (member == note.getReceiver()) {
			note.deleteByReceiver(); //받은사람 메세지 삭제
			if(note.isDeleted()) {
				//받은사람과 보낸사람 모두 삭제했을 시에 DB에서 삭제
				noteRepository.delete(note);
				return "DB에서 쪽지 완전 삭제 ";
			}else {
				noteRepository.save(note);
				return "받은사람 쪽지 삭제";
			}
		}else {
			return new IllegalArgumentException("이미 삭제된 쪽지입니다.");
		}

	}
	

	@Transactional
	@Override
	public List<NoteDto> sentNote(Member member) throws Exception {
		// TODO Auto-generated method stub
		
		 List<Note> notes = noteRepository.findAllBySender(member);
	        List<NoteDto> noteDto = new ArrayList<>();

	        for(Note note : notes) {
	            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
	            if(!note.isDeletedBySender()) {
	            	noteDto.add(NoteDto.toNote(note));
	            }
	        }
	        return noteDto;
	}
	
	@Transactional
	@Override
	public Object deleteNoteBySender(Long noteId, Member member) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("noteId" + noteId);
		Note note = noteRepository.findById(noteId).orElseThrow(()-> {
			return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
		});
//			Long memberId = JwtUtil.getCurrentMemberId();
			
//		if (memberId.equals(note.getSender().getMemberId())) {
		if (member == note.getSender()) {
			note.deleteBySender(); //받은사람 메세지 삭제
			if(note.isDeleted()) {
				//받은사람과 보낸사람 모두 삭제했을 시에 DB에서 삭제
				noteRepository.delete(note);
				return "DB에서 쪽지 완전 삭제 ";
			}else {
				noteRepository.save(note);
				return "받은사람 쪽지 삭제";
			}
		}else {
			return new IllegalArgumentException("유저 정보가 일치하지않습니다.");
		}

	}
	
	@Transactional
	@Override
	public NoteDto detailtNote(Long noteId) throws Exception {
		  // noteId를 사용하여 데이터베이스 등에서 해당 쪽지의 정보를 조회
	    Note note = noteRepository.findById(noteId).orElseThrow(() -> {
	    	return new IllegalArgumentException("쪽지를 찾을 수 없습니다.");
	    });
	    // NoteDto로 변환하여 반환
	    NoteDto noteDto = NoteDto.toNote(note);

	    return noteDto;
	}

	@Override
	public void noteStatus(Long noteId) throws Exception {
	    // 데이터베이스에서 쪽지를 조회
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new IllegalArgumentException("쪽지를 찾을 수 없습니다."));

        // 쪽지의 읽음 상태를 업데이트
        note.markAsRead();
        noteRepository.save(note);
	}


}

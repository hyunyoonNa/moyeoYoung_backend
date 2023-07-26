package com.kosta.moyoung.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.note.dto.NoteDto;
import com.kosta.moyoung.note.entity.Note;
import com.kosta.moyoung.note.repository.NoteRepository;
import com.kosta.moyoung.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
	
	private final NoteRepository noteRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper modelmapper;
	
	@Transactional
	@Override
	public void write(NoteDto noteDto) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(noteDto.toString());
		Optional<Member> receiver  = memberRepository.findByNickname(noteDto.getReceiverNickname());
		System.out.println("받는사람 : " + receiver.get().getNickname());
		Optional<Member> sender = memberRepository.findByNickname(noteDto.getSenderNickname());
		System.out.println("보낸사람 : " + sender.get().getNickname());
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
		Note note = noteRepository.findById(noteId).orElseThrow(()-> {
			return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
		});
//			System.out.println("member" + member);
//			System.out.println("sender" + note.getSender());
//			System.out.println("memberId " + member.getMemberId());
			Long memberId = JwtUtil.getCurrentMemberId();
			
			
//		if (memberId.equals(note.getSender().getMemberId())) {
		if (member == note.getReceiver()) {
			note.deleteByReceiver(); //받은사람 메세지 삭제
			System.out.println(note.isDeletedByReceiver());
			System.out.println(note.isDeletedBySender());
			System.out.println(note.isDeleted());
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

	@Override
	public Page<NoteDto> getReceivedNotesByPage(Member member, int page, int pageSize) {
	    // NoteRepository를 사용하여 받은 쪽지 데이터를 해당 페이지만큼 최신순으로 가져옵니다.
	    Page<Note> receivedNotesPage = noteRepository.findAllByReceiverOrderBySendDateDesc(member, PageRequest.of(page, pageSize));
	    List<NoteDto> noteDto = new ArrayList<NoteDto>();
	    List<Note> notes = noteRepository.findAllByReceiver(member);
	    for (Note note : receivedNotesPage) {
			if(!note.isDeletedByReceiver()) {
				NoteDto noteDTO = new NoteDto();
				noteDTO.setContent(note.getContent());
				noteDTO.setNoteId(note.getNoteId());
				noteDTO.setReceiverNickname(note.getReceiver().getNickname());
				noteDTO.setSendDate(note.getSendDate());
				noteDTO.setSenderNickname(note.getSender().getNickname());
				noteDTO.setStatus(note.isStatus());
				noteDto.add(noteDTO);
			}
		}
	    // NoteDto로 변환하여 반환
	    Page<NoteDto> noteDtoPage = new PageImpl<>(noteDto);
	    return noteDtoPage;
	}	
	
	@Override
	public Page<NoteDto> getSentNotesByPage(Member member, int page, int pageSize) {
	    // NoteRepository를 사용하여 보낸 쪽지 데이터를 해당 페이지만큼 최신순으로 가져옵니다.
	    Page<Note> sentNotesPage = noteRepository.findAllBySenderOrderBySendDateDesc(member, PageRequest.of(page, pageSize));
	    List<NoteDto> noteDtoList = new ArrayList<>();
	    for (Note note : sentNotesPage) {
	        if(!note.isDeletedBySender()) {
	            NoteDto noteDto = new NoteDto();
	            noteDto.setContent(note.getContent());
	            noteDto.setNoteId(note.getNoteId());
	            noteDto.setReceiverNickname(note.getReceiver().getNickname());
	            noteDto.setSendDate(note.getSendDate());
	            noteDto.setSenderNickname(note.getSender().getNickname());
	            noteDto.setStatus(note.isStatus());
	            noteDtoList.add(noteDto);
	        }
	    }
	    // NoteDto로 변환하여 반환
	    return new PageImpl<>(noteDtoList, sentNotesPage.getPageable(), sentNotesPage.getTotalElements());
	}

}
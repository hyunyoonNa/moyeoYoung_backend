package com.kosta.moyoung.note.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kosta.moyoung.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noteId;


	@Setter
	@Column(nullable = false, length = 300)
	private String content;

	@Column
	private String sendDate;


	@Setter
	@Column(length = 1)
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Member sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receive_id")
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private Member receiver;

	@Column(nullable = false)
	private boolean deletedBySender;

	@Column(nullable = false)
	private boolean deletedByReceiver;

	public void deleteBySender() {
		this.deletedBySender = true;
	}

	public void deleteByReceiver() {
		this.deletedByReceiver = true;
	}

	public boolean isDeleted() {
		return isDeletedBySender() && isDeletedByReceiver();
	}

	@PrePersist
	protected void onCreate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");
		sendDate = LocalDateTime.now().format(formatter);
	}

	@Builder
	public Note(String content, String sendDate, String receiveDate, String status,
			Member sender, Member receiver, boolean deletedBySender, boolean deletedByReceiver) {
		this.content = content;
		this.sendDate = sendDate;
		this.status = status;
		this.sender = sender;
		this.receiver = receiver;
		this.deletedBySender = deletedBySender;
		this.deletedByReceiver = deletedByReceiver;
	}
}

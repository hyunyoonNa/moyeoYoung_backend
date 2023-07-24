package com.kosta.moyoung.notification.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import com.kosta.moyoung.member.entity.Member;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name="jsonb",typeClass = JsonBinaryType.class)
public class Notification extends EntityDate{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarmId")
	private Long alarmId;
	
//	@Embedded
//	private NotificationContent content;
	
	@Column
	private String message;
	
	@Column
	private String link;
	
	@Enumerated(EnumType.STRING)
    private NotificationType type;
    
    @Column(nullable = false)
    private Boolean isRead;
    
    @Column(nullable = false)
    private Boolean isDeleted;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "recevier_id")
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Member recevier;
    
    @Type(type="jsonb")
    @Column(columnDefinition = "json")
    private NotificationArgs nargs;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Builder
    public Notification(Member sender, Member recevier, String message, String link, Boolean isRead, NotificationType type ) {
    	this.sender = sender;
    	this.recevier = recevier;
//    	this.content = new NotificationContent(content);
    	this.message = message;
    	this.link = link;
    	this.isRead = isRead;	
    	this.type = type;
    }
    
//    public String getContent() {
//        return content.getContent();
//    }

//    public String getUrl() {
//        return url.getUrl();
//    }
	
}

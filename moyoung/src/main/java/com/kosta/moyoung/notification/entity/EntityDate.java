package com.kosta.moyoung.notification.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityDate {
	
	@CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;
	
	@LastModifiedDate
    protected LocalDateTime updatedAt;
}

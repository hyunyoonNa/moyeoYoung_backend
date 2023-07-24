package com.kosta.moyoung.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.moyoung.notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

}

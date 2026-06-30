package com.evenza.notification_service.repository;
import com.evenza.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository <Notification, Integer >{
}

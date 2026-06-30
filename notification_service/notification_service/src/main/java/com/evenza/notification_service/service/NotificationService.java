package com.evenza.notification_service.service;

import com.evenza.notification_service.model.Notification;
import com.evenza.notification_service.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications(){
        return notificationRepository.findAll();
    }
    public Notification CreateNotifications(Notification n){
        return notificationRepository.save(n);
    }
    public void  DeleteNotifications(int id){
        notificationRepository.deleteById(id);
    }
}

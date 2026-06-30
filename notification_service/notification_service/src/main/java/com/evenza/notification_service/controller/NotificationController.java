package com.evenza.notification_service.controller;

import com.evenza.notification_service.model.Notification;
import com.evenza.notification_service.repository.NotificationRepository;
import com.evenza.notification_service.service.EmailService;
import com.evenza.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/notifications")

public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public List< Notification> getAllPayment(){
        return notificationService.getAllNotifications();
    }
    @PostMapping
    public Notification CreatePayment(@RequestBody  Notification n ){
        return notificationService.CreateNotifications(n);
    }
    @DeleteMapping ("/{id}")
    public  void  DeletePayment (@PathVariable int id){
        notificationService.DeleteNotifications(id);
    }
    @GetMapping("/count")
    public long getNotificationCount() {
        return notificationService.getAllNotifications().size();
    }
    @PostMapping("/send-email")
    public String getEmail (@RequestParam String to ,  @RequestParam String subject , @RequestParam String body ){
        emailService.sendEmail(to , subject , body);
        Notification notification = new Notification();
        notification.setMessage(body);
        notification.setStatus("SENT");
        notificationRepository.save(notification);
        return "Email sent successfully!";
    }


}

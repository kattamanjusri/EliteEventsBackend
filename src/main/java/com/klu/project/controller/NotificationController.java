package com.klu.project.controller;

import com.klu.project.model.Notification;
import com.klu.project.dao.NotificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationDao notificationDao;

    @GetMapping
    public List<Notification> getUnreadNotifications(@RequestParam String username) {
        return notificationDao.findByCustomerUsernameAndIsReadFalseOrderByCreatedAtDesc(username);
    }

    @GetMapping("/all")
    public List<Notification> getAllNotifications(@RequestParam String username) {
        return notificationDao.findByCustomerUsernameOrderByCreatedAtDesc(username);
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        notification.setCreatedAt(new Date());
        return notificationDao.save(notification);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable int id) {
        notificationDao.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> clearNotifications(@RequestParam String username) {
        notificationDao.deleteByCustomerUsername(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/event-status")
    public Notification createEventStatusNotification(@RequestBody Map<String, Object> payload) {
        Notification notification = new Notification();
        notification.setEventId(Integer.valueOf(payload.get("eventId").toString()));
        notification.setEventName((String) payload.get("eventName"));
        notification.setStatus((String) payload.get("status"));
        notification.setCustomerUsername((String) payload.get("customerUsername"));
        return notificationDao.save(notification);
    }
}
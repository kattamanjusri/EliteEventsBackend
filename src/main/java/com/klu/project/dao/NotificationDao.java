package com.klu.project.dao;

import com.klu.project.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, Integer> {
    
    // Find unread notifications for a customer
    List<Notification> findByCustomerUsernameAndIsReadFalseOrderByCreatedAtDesc(String customerUsername);
    
    // Find all notifications for a customer (both read and unread)
    List<Notification> findByCustomerUsernameOrderByCreatedAtDesc(String customerUsername);
    
    // Mark a notification as read
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.id = ?1")
    void markAsRead(int id);
    
    // Mark all notifications as read for a customer
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.customerUsername = ?1")
    void markAllAsRead(String customerUsername);
    
    // Delete notifications for a customer
    @Modifying
    @Transactional
    void deleteByCustomerUsername(String customerUsername);
}
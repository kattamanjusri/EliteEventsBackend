package com.klu.project.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "event_id")
    private int eventId;
    
    @Column(name = "event_name")
    private String eventName;
    
    private String status;
    
    @Column(name = "customer_username")
    private String customerUsername;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "is_read")
    private boolean isRead = false;

    // Constructors, Getters and Setters
    public Notification() {
    }

    public Notification(int eventId, String eventName, String status, String customerUsername) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.status = status;
        this.customerUsername = customerUsername;
        this.createdAt = new Date();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
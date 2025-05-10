package com.klu.project.model;

import jakarta.persistence.*;
@Entity
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public int getCapacity() {
	return capacity;
}
public void setCapacity(int capacity) {
	this.capacity = capacity;
}
public String getVenue() {
	return venue;
}
public void setVenue(String venue) {
	this.venue = venue;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Manager getManager() {
	return manager;
}
public void setManager(Manager manager) {
	this.manager = manager;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getEventName() {
	return eventName;
}
public void setEventName(String eventName) {
	this.eventName = eventName;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
@Column(nullable = true, length = 100)
  private String role;

  @Column(nullable = false, length = 500)
  private String description;

  @Column(nullable = false)
  private int capacity;

  @Column(nullable = false)
  private String venue;

  @Column(nullable = false)
  private String date;

  @Column(nullable = false)
  private String status = "Pending";
  private String cost; // Added cost field
  public String getCost() {
	return cost;
}
public void setCost(String cost) {
	this.cost = cost;
}
public boolean isCostSent() {
	return costSent;
}
public void setCostSent(boolean costSent) {
	this.costSent = costSent;
}
private boolean costSent = false; // Track if cost email was sent

  // Optional manager field
@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "manager_id", nullable = true)
  private Manager manager;
  
private String email;
  public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
private String phone;
  private String eventName;
  private String customerName;
  private String customerUsername; // add this
  private String managerName;

public String getManagerName() {
	return managerName;
}
public void setManagerName(String managerName) {
	this.managerName = managerName;
}
public String getCustomerUsername() {
	return customerUsername;
}
public void setCustomerUsername(String customerUsername) {
	this.customerUsername = customerUsername;
}
@Override
public String toString() {
    return "Event [id=" + id + ", customerName=" + customerName + ", customerUsername=" + customerUsername 
        + ", phone=" + phone + ", email=" + email + ", eventName=" + eventName + ", venue=" + venue 
        + ", date=" + date + ", capacity=" + capacity + ", description=" + description + ", status=" + status 
        + ", cost=" + cost + ", costSent=" + costSent + "]";
}
 

}

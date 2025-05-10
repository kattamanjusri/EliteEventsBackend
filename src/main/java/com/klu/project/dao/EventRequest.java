package com.klu.project.dao;

import com.klu.project.model.Event;

public class EventRequest {
    private String eventName;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String customerName;
    private String phone;
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String email;
    private String venue;
    private String date;
    private int capacity;
    private String description;
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
	public Event toEvent() {
        Event event = new Event();
        event.setEventName(this.eventName);
        event.setCustomerName(this.customerName);
        event.setPhone(this.phone);
        event.setEmail(this.email);
        event.setVenue(this.venue);
        event.setDate(this.date);
        event.setCapacity(this.capacity);
        event.setDescription(this.description);
        event.setCustomerUsername(this.customerUsername);
        event.setManagerName(this.managerName);
        
        
        return event;
    }
}

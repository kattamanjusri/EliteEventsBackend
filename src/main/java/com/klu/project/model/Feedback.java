package com.klu.project.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event; // Assuming Event is another entity representing events
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public String getFeedbackText() {
		return feedbackText;
	}
	public void setFeedbackText(String feedbackText) {
		this.feedbackText = feedbackText;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public LocalDateTime getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(LocalDateTime feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	private String feedbackText;
    private int rating;
    private LocalDateTime feedbackDate;
    
    // Constructors, getters, and setters
}
package com.klu.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klu.project.model.Feedback;
import com.klu.project.model.Event;
import com.klu.project.dao.EventDao;
import com.klu.project.service.FeedbackService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EventDao eventDao;

    // Admin: Fetch feedback for a specific event (e.g. show in AdminHome)
    @GetMapping("/event/{id}")
    public List<Feedback> getFeedbackForEvent(@PathVariable int id) {
        return feedbackService.getFeedbackForEvent(id);
    }

    // Admin: View all feedback if needed
    @GetMapping("/all")
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    // Customer: Submit feedback for an event
    @PostMapping("/submit")
    public String submitFeedback(@RequestBody FeedbackRequest request) {
        Event event = eventDao.findById(request.getEventId()).orElse(null);
        if (event == null) {
            return "Event not found!";
        }

        Feedback feedback = new Feedback();
        feedback.setEvent(event);
        feedback.setFeedbackText(request.getFeedbackText());
        feedback.setRating(request.getRating());
        feedback.setFeedbackDate(LocalDateTime.now());

        feedbackService.saveFeedback(feedback);
        return "Feedback submitted successfully!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable int id) {
        // Find feedback by ID
        Feedback feedback = feedbackService.getFeedbackById(id);
        
        if (feedback == null) {
            return "Feedback not found!";
        }

        try {
            // Delete feedback
            feedbackService.deleteFeedback(feedback);
            return "Feedback deleted successfully!";
        } catch (Exception e) {
            // Handle any errors that occur during deletion
            return "Error occurred while deleting feedback: " + e.getMessage();
        }
    }
    
    
    // Inner DTO class
    public static class FeedbackRequest {
        private int eventId;
        private String feedbackText;
        private int rating;

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
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
    }
}

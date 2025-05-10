package com.klu.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.project.dao.FeedbackDao;
import com.klu.project.model.Feedback;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    public void saveFeedback(Feedback feedback) {
        feedbackDao.save(feedback);
    }

    public List<Feedback> getFeedbackForEvent(int eventId) {
        return feedbackDao.findByEventId(eventId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackDao.findAll();
    }
    public Feedback getFeedbackById(int id) {
        return feedbackDao.findById(id).orElse(null);
    }
    // Method to delete feedback
    public void deleteFeedback(Feedback feedback) {
        feedbackDao.delete(feedback);
    }
}

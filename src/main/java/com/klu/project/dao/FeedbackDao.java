package com.klu.project.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.project.model.Feedback;

import java.util.List;
public interface FeedbackDao extends JpaRepository<Feedback, Integer> {
	 List<Feedback> findByEventId(int EventId);
}

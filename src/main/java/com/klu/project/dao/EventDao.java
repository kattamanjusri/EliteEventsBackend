package com.klu.project.dao;

import com.klu.project.model.Event;
import com.klu.project.model.Manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDao extends JpaRepository<Event, Integer> {
    List<Event> findByPhone(String phone);
    List<Event> findByManagerId(int managerId);
    List<Event> findByCustomerNameContainingIgnoreCase(String customerName);
    List<Event> findByEventNameContainingIgnoreCase(String eventName);
    List<Event> findByCustomerUsername(String username);
    List<Event> findByManager(Manager manager);
    List<Event> findByManagerName(String managerName);
   
}

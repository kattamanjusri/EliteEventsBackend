package com.klu.project.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.project.dao.EventDao;
import com.klu.project.dao.EventRequest;
import com.klu.project.dao.ManagerDao;
import com.klu.project.model.Event;
import com.klu.project.model.Manager;

@Service
public class EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ManagerDao managerDao; // Assuming you have a ManagerDao to fetch manager info

    public Event saveEvent(Event event) {
        return eventDao.save(event);
    }

    public List<Event> getAllEvents() {
        return eventDao.findAll();
    }

    public Event getEventById(int id) {
        return eventDao.findById(id).orElse(null);
    }

    public void deleteEvent(int id) {
        eventDao.deleteById(id);
    }
    
    public List<Event> getEventsByPhone(String phone) {
        return eventDao.findByPhone(phone);
    }
    
    public List<Event> getEventsByCustomerName(String customerName) {
        return eventDao.findByCustomerNameContainingIgnoreCase(customerName);
    }
    
    public List<Event> getEventsByEventName(String eventName) {
        return eventDao.findByEventNameContainingIgnoreCase(eventName);
    }

    public List<Event> getEventsByManagerId(int managerId) {
        return eventDao.findByManagerId(managerId);
    }

    public void registerEvent(EventRequest eventRequest) {
        Event event = new Event();
        event.setEventName(eventRequest.getEventName());
        event.setCustomerName(eventRequest.getCustomerName());
        event.setPhone(eventRequest.getPhone());
        event.setVenue(eventRequest.getVenue());
        event.setDate(eventRequest.getDate().toString());
        event.setCapacity(eventRequest.getCapacity());
        event.setDescription(eventRequest.getDescription());

        // Assign manager based on event type
        String managerName = getDefaultManagerForEvent(eventRequest.getEventName());
        Manager manager = managerDao.findByName(managerName);
        
        if (manager != null) {
            event.setManager(manager);
        } else {
            throw new RuntimeException("Manager not found for event type");
        }

        eventDao.save(event); // Save event with the assigned manager
    }

    private String getDefaultManagerForEvent(String eventName) {
        switch (eventName) {
            case "SangeetRegistration":
                return "Kushi Kumari";
            case "WeddingRegistration":
                return "Arnav Singh";
            case "BachelorRegistration":
                return "Neil Prathap";
            case "BirthdayRegistration":
                return "Shivay Raghuvamshi";
            case "HaldiRegistration":
                return "Avani Obrai";
            case "EngagementRegistration":
                return "Arya Rajavath";
            default:
                return "Default Manager"; // You can have a fallback manager
        }
    }

    public List<Event> getEventsByCustomerUsername(String username) {
        return eventDao.findByCustomerUsername(username);
    }
    public boolean cancelEvent(int eventId) {
        try {
            eventDao.deleteById(eventId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

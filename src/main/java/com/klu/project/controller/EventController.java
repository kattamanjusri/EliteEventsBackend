package com.klu.project.controller;

import com.klu.project.dao.EventDao;
import com.klu.project.dao.EventRequest;
import com.klu.project.dao.ManagerDao;
import com.klu.project.dao.NotificationDao;
import com.klu.project.model.Event;
import com.klu.project.model.Manager;
import com.klu.project.model.Notification;
import com.klu.project.service.EmailService;
import com.klu.project.service.EventService;
import com.klu.project.service.ManagerService;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ManagerDao managerDao;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private NotificationDao notificationDao;
    
    // Get all events for admin
    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Delete event - Updated to change status to Cancelled and send email notification
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id) {
        try {
            // Get event before deletion for email notification
            Event event = eventService.getEventById(id);
            if (event != null) {
                // Change status to Cancelled
                event.setStatus("Cancelled");
                eventService.saveEvent(event);
                
                // Send cancellation email notification
                sendStatusChangeEmail(event, "Cancelled");
                
                // Create notification for customer
                createNotificationForStatusChange(event, "Cancelled");
                
                // Then delete the event
                eventService.deleteEvent(id);
                return ResponseEntity.ok().body(Map.of("message", "Event cancelled and deleted successfully"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to delete event: " + e.getMessage()));
        }
    }

    // Update status - Updated to send email notification AND create notification
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestBody String status) {
        try {
            Event event = eventService.getEventById(id);
            if (event != null) {
                String newStatus = status.replace("\"", ""); // handle plain string body
                String oldStatus = event.getStatus();
                event.setStatus(newStatus);
                eventService.saveEvent(event);
                
                // Send status change email notification
                sendStatusChangeEmail(event, newStatus);
                
                // Create in-app notification if status changed
                if (!newStatus.equalsIgnoreCase(oldStatus)) {
                    Notification notification = createNotificationForStatusChange(event, newStatus);
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("event", event);
                    response.put("notification", notification);
                    return ResponseEntity.ok(response);
                }
                
                return ResponseEntity.ok(event);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to update status: " + e.getMessage()));
        }
    }
    
    // Helper method to create notifications for status changes
    private Notification createNotificationForStatusChange(Event event, String newStatus) {
        Notification notification = new Notification();
        notification.setEventId(event.getId());
        notification.setEventName(event.getEventName());
        notification.setStatus(newStatus);
        notification.setCustomerUsername(event.getCustomerUsername());
        notification.setCreatedAt(new Date());
        notification.setRead(false);
        return notificationDao.save(notification);
    }
    
    // Helper method to send status change email notifications
    private void sendStatusChangeEmail(Event event, String newStatus) throws MessagingException {
        String subject = "Event Status Update - " + event.getEventName();
        
        // Customize message based on status
        String statusMessage;
        switch(newStatus) {
            case "In Progress":
                statusMessage = "We're excited to inform you that the planning for your event is now <strong>in progress</strong>. Our team is working diligently to prepare everything for your special occasion.";
                break;
            case "Completed":
                statusMessage = "We're pleased to inform you that your event has been <strong>completed</strong>. Thank you for choosing Elite Events for your special occasion. We hope you had a wonderful experience!";
                break;
            case "Cancelled":
                statusMessage = "We regret to inform you that your event has been <strong>cancelled</strong>. If you have any questions or concerns, please contact our team.";
                break;
            default: // Pending or any other status
                statusMessage = "Your event status has been updated to <strong>" + newStatus + "</strong>. Our team will keep you informed of any further developments.";
        }
        
        // HTML email body with status update
        String htmlBody = String.format(
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333333; }" +
            ".container { padding: 20px; }" +
            ".status-details { margin: 20px 0; padding: 15px; background-color: #f5f5f5; border-left: 4px solid #3498db; }" +
            ".status { font-size: 18px; font-weight: bold; color: #2c3e50; }" +
            ".event-details { margin: 15px 0; }" +
            ".detail-item { margin: 5px 0; }" +
            ".detail-label { font-weight: bold; }" +
            ".signature { margin-top: 30px; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<p>Dear %s,</p>" +
            "<p>%s</p>" +
            "<div class='status-details'>" +
            "<p>Current Status: <span class='status'>%s</span></p>" +
            "</div>" +
            "<div class='event-details'>" +
            "<p><strong>Event Details:</strong></p>" +
            "<p class='detail-item'><span class='detail-label'>Event Name:</span> %s</p>" +
            "<p class='detail-item'><span class='detail-label'>Venue:</span> %s</p>" +
            "<p class='detail-item'><span class='detail-label'>Date:</span> %s</p>" +
            "</div>" +
            "<p>For any questions or further information, please don't hesitate to contact us.</p>" +
            "<div class='signature'>" +
            "<p>Warm regards,<br>Elite Events Team</p>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>",
            event.getCustomerName(), statusMessage, newStatus, 
            event.getEventName(), event.getVenue(), event.getDate()
        );
        
        // Send email with HTML content
        emailService.sendHtmlEmail(event.getEmail(), subject, htmlBody);
    }
    
    
    
    
    @PostMapping("/register")
    public ResponseEntity<?> registerEvent(@RequestBody EventRequest request) {
        try {
            // Debug: Log the manager name received in the request
            System.out.println("Received manager name: " + request.getManagerName());
            
            // Fetch the manager by name - improved with more detailed logging
            Manager manager = managerService.getManagerByName(request.getManagerName());
            
            if (manager == null) {
                System.out.println("Manager not found with name: " + request.getManagerName());
                // Try to find by trimming whitespace or similar names
                List<Manager> allManagers = managerDao.findAll();
                System.out.println("Available managers: " + allManagers);
                
                // Fallback: Try to get the first manager with a similar name or role
                for (Manager m : allManagers) {
                    if (m.getName().contains(request.getManagerName()) || 
                        request.getManagerName().contains(m.getName())) {
                        manager = m;
                        System.out.println("Using similar manager: " + manager.getName());
                        break;
                    }
                    
                    // If there's a Wedding Manager when eventName is Wedding
                    if ("Wedding".equals(request.getEventName()) && 
                        "Wedding Manager".equals(m.getRole())) {
                        manager = m;
                        System.out.println("Using wedding manager: " + manager.getName());
                        break;
                    }
                }
                
                // If still no manager found, try to get the first one or return error
                if (manager == null) {
                    if (!allManagers.isEmpty()) {
                        manager = allManagers.get(0);
                        System.out.println("Using first available manager: " + manager.getName());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("message", "Manager not found and no fallback available"));
                    }
                }
            }

            // Convert EventRequest to Event and associate manager
            Event event = request.toEvent();
            event.setManager(manager);
            event.setCustomerUsername(request.getCustomerUsername());
            
            // Save the event
            event = eventService.saveEvent(event);

            // Send email to manager

            // Send email to customer
            String subject = "Event Registration Successful";
            String htmlBody = String.format(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333333; }" +
                ".container { padding: 20px; }" +
                ".event-details { margin: 20px 0; }" +
                ".detail-item { margin: 10px 0; }" +
                ".detail-label { font-weight: bold; }" +
                ".signature { margin-top: 30px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<p>Dear %s,</p>" +
                "<p>We're delighted to confirm that your event '%s' has been successfully registered! 🎉</p>" +
                "<div class='event-details'>" +
                "<p><strong>Event Details:</strong></p>" +
                "<p class='detail-item'><span class='detail-label'>Venue:</span> %s</p>" +
                "<p class='detail-item'><span class='detail-label'>Date:</span> %s</p>" +
                "<p class='detail-item'><span class='detail-label'>Capacity:</span> %d guests</p>" +
                "</div>" +
                "<p>We will send you detailed cost information shortly.</p>" +
                "<p>Soon our manager will contact you...</p>" +
                "<p>Thank you for choosing Elite Events to bring your special occasion to life! We look forward to creating an unforgettable experience for you and your guests. 🤗</p>" +
                "<div class='signature'>" +
                "<p>Warm regards,<br>Elite Events Team</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>",
                request.getCustomerName(), request.getEventName(), request.getVenue(),
                request.getDate(), request.getCapacity()
            );

            emailService.sendHtmlEmail(request.getEmail(), subject, htmlBody);

            return ResponseEntity.ok(Map.of(
                "message", "Event registered successfully! Email sent to manager and customer.",
                "assignedManager", manager.getName()
            ));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Event registered, but failed to send email."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Failed to register event. Error: " + e.getMessage()));
        }
    }

    // Get event by phone
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getEventsByPhone(@PathVariable String phone) {
        List<Event> events = eventService.getEventsByPhone(phone);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    // Get events by customer name
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<?> getEventsByCustomerName(@PathVariable String customerName) {
        List<Event> events = eventService.getEventsByCustomerName(customerName);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    // Get events by event name
    @GetMapping("/name/{eventName}")
    public ResponseEntity<?> getEventsByEventName(@PathVariable String eventName) {
        List<Event> events = eventService.getEventsByEventName(eventName);
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getEventsByCustomerUsername(@PathVariable String username) {
        List<Event> events = eventService.getEventsByCustomerUsername(username);
        System.out.println("Events fetched for username " + username + ": " + events); 
        if (events.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(events);
    }

    @PostMapping("/send-cost")
    public ResponseEntity<?> sendCostEmail(@RequestBody Map<String, Object> request) {
        try {
            // Extract data from request
            Integer eventId = (Integer) request.get("eventId");
            String email = (String) request.get("email");
            String customerName = (String) request.get("customerName");
            String eventName = (String) request.get("eventName");
            String cost = request.get("cost").toString();
            
            // Update event cost in database
            if (eventId != null) {
                Event event = eventService.getEventById(eventId);
                if (event != null) {
                    event.setCost(cost);
                    eventService.saveEvent(event);
                }
            }

            // Prepare email
            String subject = "Event Cost Information - " + eventName;
            
            // HTML email body with cost details
            String htmlBody = String.format(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333333; }" +
                ".container { padding: 20px; }" +
                ".cost-details { margin: 20px 0; padding: 15px; background-color: #f5f5f5; border-left: 4px solid #3498db; }" +
                ".cost-amount { font-size: 24px; font-weight: bold; color: #2c3e50; }" +
                ".note { font-style: italic; color: #7f8c8d; }" +
                ".signature { margin-top: 30px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<p>Dear %s,</p>" +
                "<p>We're pleased to provide you with the cost information for your event <strong>'%s'</strong>.</p>" +
                "<div class='cost-details'>" +
                "<p>The total cost for your event has been calculated as:</p>" +
                "<p class='cost-amount'>₹%s</p>" +
                "</div>" +
                "<p>This amount covers venue arrangements, basic decorations, and our event management services.</p>" +
                "<p class='note'>Note: Additional services requested during the event may incur extra charges.</p>" +
                "<p>If you have any questions about the pricing or would like to discuss payment options, please feel free to contact us.</p>" +
                "<div class='signature'>" +
                "<p>Warm regards,<br>Elite Events Team</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>",
                customerName, eventName, cost
            );
            
            // Send email with HTML content
            emailService.sendHtmlEmail(email, subject, htmlBody);

            return ResponseEntity.ok().body(Map.of("message", "Cost information sent successfully"));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Failed to send email"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
  
    @GetMapping("/manager/{managerName}/events")
    public ResponseEntity<List<Event>> getEventsByManager(@PathVariable String managerName) {
        List<Event> events = eventDao.findByManagerName(managerName);
        return ResponseEntity.ok(events);
    }
    
    @DeleteMapping("/cancel/{eventId}")
    public ResponseEntity<?> cancelEvent(@PathVariable int eventId, @RequestParam String customerUsername) {
        try {
            // Fetch the event to verify ownership
            Event event = eventService.getEventById(eventId);
            
            if (event == null) {
                return ResponseEntity.status(404).body("Event not found");
            }
            
            // Check if the event belongs to the requesting customer
            if (!event.getCustomerUsername().equals(customerUsername)) {
                return ResponseEntity.status(403).body("You don't have permission to cancel this event");
            }
            
            // Cancel the event
            boolean cancelled = eventService.cancelEvent(eventId);
            
            if (cancelled) {
                return ResponseEntity.ok("Event cancelled successfully");
            } else {
                return ResponseEntity.status(500).body("Failed to cancel event");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to cancel event: " + e.getMessage());
        }
    }

    
    
}
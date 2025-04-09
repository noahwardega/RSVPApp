package com.planit.enterprise;

<<<<<<< Updated upstream
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
=======
import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> Stashed changes

@Controller
public class PlanItController {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IEventService eventService;
    
    // Map to store mock events for each user
    private final Map<Integer, List<EventDTO>> userEvents = new HashMap<>();
    
    // Constructor to initialize mock events
    public PlanItController() {
        // Initialize mock events for each team member
        initializeMockEvents();
    }
    
    private void initializeMockEvents() {
        // Events for Emma (ID: 1)
        List<EventDTO> emmaEvents = new ArrayList<>();
        emmaEvents.add(createMockEvent(1, "Team Meeting", "2025-03-20", "Conference Room A"));
        emmaEvents.add(createMockEvent(2, "Project Deadline", "2025-04-15", "Online"));
        userEvents.put(1, emmaEvents);
        
        // Events for Dominic (ID: 2)
        List<EventDTO> dominicEvents = new ArrayList<>();
        dominicEvents.add(createMockEvent(3, "Client Presentation", "2025-03-22", "Main Auditorium"));
        dominicEvents.add(createMockEvent(4, "Team Lunch", "2025-03-25", "Cafeteria"));
        dominicEvents.add(createMockEvent(5, "Code Review", "2025-04-01", "Meeting Room 3"));
        userEvents.put(2, dominicEvents);
        
        // Events for Noah (ID: 3)
        List<EventDTO> noahEvents = new ArrayList<>();
        noahEvents.add(createMockEvent(6, "Database Workshop", "2025-03-19", "Training Room"));
        noahEvents.add(createMockEvent(7, "Sprint Planning", "2025-03-27", "Zoom Call"));
        userEvents.put(3, noahEvents);
        
        // Events for Calvin (ID: 4)
        List<EventDTO> calvinEvents = new ArrayList<>();
        calvinEvents.add(createMockEvent(8, "UI/UX Review", "2025-03-21", "Design Lab"));
        calvinEvents.add(createMockEvent(9, "Frontend Demo", "2025-03-28", "Conference Room B"));
        calvinEvents.add(createMockEvent(10, "Team Building", "2025-04-10", "City Park"));
        userEvents.put(4, calvinEvents);
        
        // Events for Debbie (ID: 5)
        List<EventDTO> debbieEvents = new ArrayList<>();
        debbieEvents.add(createMockEvent(11, "Staff Meeting", "2025-03-18", "Board Room"));
        debbieEvents.add(createMockEvent(12, "Project Review", "2025-03-26", "Meeting Room 1"));
        userEvents.put(5, debbieEvents);
    }
    
    private EventDTO createMockEvent(int id, String name, String date, String location) {
        EventDTO event = new EventDTO();
        event.setId(id);
        event.setName(name);
        event.setDate(date);
        event.setLocation(location);
        return event;
    }
    
    /**
     * Handle the root (/) endpoint and return a start page
     * @return
     */
    @RequestMapping("/")
<<<<<<< Updated upstream
    public String index(){
        return "start";
    }
=======
    public String signIn(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "signIn";
    }

    @RequestMapping("/signIn")
    public String start(@ModelAttribute UserDTO userDTO, Model model){
        // Check if email exists in our system
        if (userDTO != null && userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            // Debug logging
            System.out.println("Attempting to sign in with email: " + userDTO.getEmail());
            
            // Get user from service or create a new one if it doesn't exist
            UserDTO loggedInUser = userService.fetchUserByEmail(userDTO.getEmail());
            
            if (loggedInUser == null) {
                // Create a new user with this email
                loggedInUser = new UserDTO();
                loggedInUser.setEmail(userDTO.getEmail());
                loggedInUser.setId(100); // Use a high ID to avoid conflicts with team members
                loggedInUser.setFName("Guest");
                loggedInUser.setLName("User");
                
                // No default events for guest users
                userEvents.put(100, new ArrayList<>());
            } else {
                // Override the name to always show "Guest" regardless of the actual user
                loggedInUser.setFName("Guest");
                loggedInUser.setLName("User");
            }
            
            // Debug logging
            System.out.println("User found or created: " + (loggedInUser != null));
            
            // Add user to model for display
            model.addAttribute("user", loggedInUser);
            
            // Get upcoming events for this user
            List<EventDTO> upcomingEvents = getUpcomingEventsForUser(loggedInUser.getId());
            model.addAttribute("upcomingEvents", upcomingEvents);
            
            return "start";
        }
        
        // If login fails, return to sign-in page with error message
        model.addAttribute("errorMessage", "Please enter an email address.");
        model.addAttribute("userDTO", new UserDTO());
        return "signIn";
    }

    @RequestMapping("/saveEvent")
    public String saveEvent(@ModelAttribute EventDTO eventDTO, @RequestParam(required = false) String inviteeIds, Model model){
        // Save the event
        int eventId = eventService.addEvent(eventDTO.getName(), eventDTO.getDate(), eventDTO.getLocation());
        
        // Process invitees if any were selected
        if (inviteeIds != null && !inviteeIds.isEmpty()) {
            String[] inviteeIdArray = inviteeIds.split(",");
            // Here you would process the invitees, but for now we'll just log them
            System.out.println("Invitees for event " + eventDTO.getName() + ": " + inviteeIds);
        }
        
        // Always use Guest account (ID 100)
        UserDTO guestUser = new UserDTO();
        guestUser.setId(100);
        guestUser.setEmail("guest@example.com");
        guestUser.setFName("Guest");
        guestUser.setLName("User");
        model.addAttribute("user", guestUser);
        
        // Add the new event to Guest's events
        EventDTO newEvent = createMockEvent(100 + userEvents.size(), eventDTO.getName(), eventDTO.getDate(), eventDTO.getLocation());
        
        // Get or create the guest event list
        List<EventDTO> guestEventList = userEvents.getOrDefault(100, new ArrayList<>());
        
        // Clear any existing events and add only the new one
        guestEventList.clear();
        guestEventList.add(newEvent);
        userEvents.put(100, guestEventList);
        
        // Get updated events (should be just the one we added)
        List<EventDTO> upcomingEvents = getUpcomingEventsForUser(100);
        model.addAttribute("upcomingEvents", upcomingEvents);
        
        return "start";
    }

    /* Sends user to createEvent page when Create Event button is pressed */
    @RequestMapping("/createEvent")
    public String createEvent(Model model){
        EventDTO event = new EventDTO();
        model.addAttribute("eventDTO", event);
        
        // Add Guest user to model
        UserDTO guestUser = new UserDTO();
        guestUser.setId(100);
        guestUser.setEmail("guest@example.com");
        guestUser.setFName("Guest");
        guestUser.setLName("User");
        model.addAttribute("user", guestUser);
        
        return "createEvent";
    }
    
    /**
     * Helper method to get upcoming events for a user
     */
    private List<EventDTO> getUpcomingEventsForUser(int userId) {
        // Return events from our mock data
        return userEvents.getOrDefault(userId, new ArrayList<>());
    }

    /* @GetMapping("/event")
    /* @ResponseBody
    /* public List<Event> getAllEvents() { return IEventService.getAllEvents(); }
     */
/*
    @PostMapping(value="/event", consumes="application/json", produces="application/json")
    @ResponseBody
    public EventDTO createEvent(@ResponseBody EventDTO event){
        EventDTO newEvent = null;
        try{
            newEvent = eventService.save(event);
        } catch (Exception e) {

        }
        return newEvent;
    }

 */
>>>>>>> Stashed changes
}

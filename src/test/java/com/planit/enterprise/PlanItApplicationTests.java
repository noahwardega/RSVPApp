package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.service.interfaces.IEventService;
import com.planit.enterprise.service.interfaces.IRSVPService;
import com.planit.enterprise.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlanItApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private IEventService eventService;

    @Autowired
    private IRSVPService rsvpService;

    private User testUser;
    private EventDTO testEvent;
    private int userId;
    private int eventId;

    @BeforeEach
    void setup() {
        // Save test user
        userId = userService.registerUser("Test", "Test", "Test@email.com");
        testUser = userService.getUserById(userId);

        // Save test event
        testEvent = eventService.createEvent("Test Event", "New York", LocalDateTime.of(2025, 4, 1, 10, 0), userId);
        eventId = testEvent.getId();  // Retrieve the event ID from the DTO

        // Ensure both are saved before creating an RSVP
        assertNotNull(testUser);
        assertNotNull(testEvent);
    }

    @Test
    void contextLoads() {
        assertNotNull(userService);
        assertNotNull(eventService);
        assertNotNull(rsvpService);
    }

    @Test
    void fetchUserByID_returnsCorrectUser() {
        User user = userService.getUserById(userId);
        assertNotNull(user);
        assertEquals("Test", user.getFName());
        assertEquals("Test", user.getLName());
    }

    @Test
    void fetchUserByEmail_returnsCorrectUser() {
        Optional<User> optionalUser = userService.getUserByEmail("Test@email.com");

        assertTrue(optionalUser.isPresent(), "User should be found");

        User user = optionalUser.get();
        assertEquals("Test", user.getFName());
    }

    @Test
    void doesEmailExist_returnsTrueForExistingEmail() {
        assertTrue(userService.existsByEmail("Test@email.com"));
        assertFalse(userService.existsByEmail("fake@email.com"));
    }

    @Test
    void registerUser_createsNewUserSuccessfully() {
        int newUserId = userService.registerUser("Jane", "Smith", "janesmith@email.com");
        assertTrue(newUserId > 0);
        User newUser = userService.getUserById(newUserId);
        assertEquals("Jane", newUser.getFName());
    }

    @Test
    void fetchEventByID_returnsCorrectEvent() {
        Event event = eventService.getEventById(eventId);
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
    }

    @Test
    void createEvent_createsNewEventSuccessfully() {
        EventDTO newEvent = eventService.createEvent("Conference", "Los Angeles", LocalDateTime.of(2025, 6, 10, 9, 0), userId);
        assertNotNull(newEvent);
        assertTrue(newEvent.getId() > 0);
        assertEquals("Conference", newEvent.getName());
    }

    @Test
    void fetchAllByUserID_returnsRSVPs() {
        // Fetch the User and Event entities (use entities here)
        User user = userService.getUserById(userId);  // Use User entity
        Event event = eventService.getEventById(eventId);  // Use Event entity

        // Create or update RSVP (works with entities)
        rsvpService.createOrUpdateRSVP(user, event, "Accepted");

        // Fetch RSVPs by User (service returns DTOs, not entities)
        List<RSVPDTO> rsvps = rsvpService.getRSVPsByUser(user);
        assertFalse(rsvps.isEmpty());
        assertEquals(eventId, rsvps.get(0).getEventId());
        assertEquals(userId, rsvps.get(0).getUserId());
        assertEquals("Accepted", rsvps.get(0).getStatus());
    }

    @Test
    void fetchAllByEventID_returnsRSVPs() {
        // Fetch the User and Event entities (use entities here)
        User user = userService.getUserById(userId);  // Use User entity
        Event event = eventService.getEventById(eventId);  // Use Event entity

        // Create or update RSVP (works with entities)
        rsvpService.createOrUpdateRSVP(user, event, "Accepted");

        // Fetch RSVPs by Event (service returns DTOs, not entities)
        List<RSVP> rsvps = rsvpService.getRSVPsByEvent(event);
        assertFalse(rsvps.isEmpty());
        assertEquals(userId, rsvps.get(0).getUserId());
        assertEquals(eventId, rsvps.get(0).getEventId());
    }

    @Test
    void createRSVP_createsNewRSVP() {
        // Fetch the User and Event entities (use entities here)
        User user = userService.getUserById(userId);  // Use User entity
        Event event = eventService.getEventById(eventId);  // Use Event entity

        // Create or update RSVP (works with entities)
        rsvpService.createOrUpdateRSVP(user, event, "Accepted");

        // Fetch RSVPs by User (service returns DTOs, not entities)
        List<RSVPDTO> rsvps = rsvpService.getRSVPsByUser(user);
        assertEquals(1, rsvps.size());
        assertEquals("Accepted", rsvps.get(0).getStatus());
    }
}







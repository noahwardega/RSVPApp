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
        // Create and save a test user
        userId = userService.registerUser("Test", "Test", "Test@email.com");
        testUser = userService.getUserById(userId);

        // Ensure the user is saved correctly
        assertNotNull(testUser);

        // Create an EventDTO object with test event data
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Test Event");
        eventDTO.setLocation("New York");
        eventDTO.setDate(LocalDateTime.of(2025, 4, 1, 10, 0));

        // Create the event by passing EventDTO and test user (as host)
        testEvent = eventService.createEvent(eventDTO, testUser);
        eventId = testEvent.getId();  // Retrieve the event ID from the DTO

        // Ensure the event was created
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
        // Create a mock user (this would normally be a user from the database)
        User currentUser = new User();
        currentUser.setId(1);  // Set an ID for the mock user
        currentUser.setFName("John");
        currentUser.setLName("Doe");
        currentUser.setEmail("john.doe@example.com");

        // Create EventDTO object
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Conference");
        eventDTO.setLocation("Los Angeles");
        eventDTO.setDate(LocalDateTime.of(2025, 6, 10, 9, 0));

        // Call the createEvent method with the mock user
        EventDTO newEvent = eventService.createEvent(eventDTO, currentUser);

        // Assertions to check if the event was created successfully
        assertNotNull(newEvent);
        assertTrue(newEvent.getId() > 0);  // ID should be greater than 0 if event is saved
        assertEquals("Conference", newEvent.getName());
        assertEquals("Los Angeles", newEvent.getLocation());
        assertEquals(LocalDateTime.of(2025, 6, 10, 9, 0), newEvent.getDate());
        assertEquals(currentUser.getId(), newEvent.getHostId());  // Host ID should match the current user's ID
    }


    @Test
    void fetchAllByUserID_returnsRSVPs() {
        // Fetch the User and Event entities (use entities here)
        User user = userService.getUserById(userId);  // Use User entity
        Event event = eventService.getEventById(eventId);  // Use Event entity

        // Create or update RSVP (works with entities)
        rsvpService.createOrUpdateRSVP(user, event, "Yes");

        // Fetch RSVPs by User (service returns DTOs, not entities)
        List<RSVPDTO> rsvps = rsvpService.getRSVPsByUser(user);
        assertFalse(rsvps.isEmpty());
        assertEquals(eventId, rsvps.get(0).getEventId()); // Verify Event ID in the RSVPDTO
        assertEquals(userId, rsvps.get(0).getUserId()); // Verify User ID in the RSVPDTO

        // Check the integer status directly
        assertEquals(1, rsvps.get(0).getRsvpStatus());  // Assert status as integer (1 for "Yes")

        // Alternatively, check the status as a string
        assertEquals("Yes", rsvps.get(0).getRsvpStatusAsString());  // Assert status as String
    }

    @Test
    void fetchAllByEventID_returnsRSVPs() {
        // Fetch the User and Event entities (use entities here)
        User user = userService.getUserById(userId);  // Use User entity
        Event event = eventService.getEventById(eventId);  // Use Event entity

        // Create or update RSVP (works with entities)
        rsvpService.createOrUpdateRSVP(user, event, "Yes");

        // Fetch RSVPs by Event (service returns DTOs, not entities)
        List<RSVPDTO> rsvps = rsvpService.getRSVPsByEvent(event);
        assertFalse(rsvps.isEmpty());
        assertEquals(userId, rsvps.get(0).getUserId()); // Verify User ID in the RSVPDTO
        assertEquals(eventId, rsvps.get(0).getEventId()); // Verify Event ID in the RSVPDTO

        // Check the integer status directly
        assertEquals(1, rsvps.get(0).getRsvpStatus());  // Assert status as integer (1 for "Yes")

        // Alternatively, check the status as a string
        assertEquals("Yes", rsvps.get(0).getRsvpStatusAsString());  // Assert status as String
    }


    @Test
    void createRSVP_createsNewRSVP() {
        // Fetch the User and Event entities (use entities here)
        User user = userService.getUserById(userId);  // Use User entity
        Event event = eventService.getEventById(eventId);  // Use Event entity

        // Create or update RSVP (works with entities)
        rsvpService.createOrUpdateRSVP(user, event, "Yes");

        // Fetch RSVPs by User (service returns DTOs, not entities)
        List<RSVPDTO> rsvps = rsvpService.getRSVPsByUser(user);
        assertEquals(1, rsvps.size()); // Ensure one RSVP is created

        // Check the integer status directly
        assertEquals(1, rsvps.get(0).getRsvpStatus());  // Assert status as integer (1 for "Yes")

        // Alternatively, check the status as a string
        assertEquals("Yes", rsvps.get(0).getRsvpStatusAsString());  // Assert status as String
    }
}







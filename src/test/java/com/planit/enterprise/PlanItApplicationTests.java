package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import com.planit.enterprise.service.interfaces.IRSVPService;
import com.planit.enterprise.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private UserDTO testUser;
    private EventDTO testEvent;
    private int userId;
    private int eventId;

    @BeforeEach
    void setup() {
        // Save test user
        userId = userService.registerUser("Debbie", "Doe", "debbiedoe@email.com");
        testUser = userService.fetchUserByID(userId);

        // Save test event
        eventId = eventService.createEvent("Test Event", "2025-04-01", "New York");
        testEvent = eventService.fetchEventByID(eventId);

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
        UserDTO user = userService.fetchUserByID(userId);
        assertNotNull(user);
        assertEquals("Debbie", user.getFName());
        assertEquals("Doe", user.getLName());
    }

    @Test
    void fetchUserByEmail_returnsCorrectUser() {
        UserDTO user = userService.fetchUserByEmail("debbiedoe@email.com");
        assertNotNull(user);
        assertEquals("Debbie", user.getFName());
    }

    @Test
    void doesEmailExist_returnsTrueForExistingEmail() {
        assertTrue(userService.doesEmailExist("Debbiedoe@email.com"));
        assertFalse(userService.doesEmailExist("fake@email.com"));
    }

    @Test
    void registerUser_createsNewUserSuccessfully() {
        int newUserId = userService.registerUser("Jane", "Smith", "janesmith@email.com");
        assertTrue(newUserId > 0);
        UserDTO newUser = userService.fetchUserByID(newUserId);
        assertEquals("Jane", newUser.getFName());
    }

    @Test
    void fetchEventByID_returnsCorrectEvent() {
        EventDTO event = eventService.fetchEventByID(eventId);
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
    }

    @Test
    void createEvent_createsNewEventSuccessfully() {
        int newEventId = eventService.createEvent("Conference", "2025-06-10", "Los Angeles");
        assertTrue(newEventId > 0);
        EventDTO newEvent = eventService.fetchEventByID(newEventId);
        assertEquals("Conference", newEvent.getName());
    }

    @Test
    void fetchAllByUserID_returnsRSVPs() {
        rsvpService.createRSVP(eventId, userId);
        List<RSVPDTO> rsvps = rsvpService.fetchAllByUserID(userId);
        assertFalse(rsvps.isEmpty());
        assertEquals(eventId, rsvps.get(0).getEventId());
    }

    @Test
    void fetchAllByEventID_returnsRSVPs() {
        rsvpService.createRSVP(eventId, userId);
        List<RSVPDTO> rsvps = rsvpService.fetchAllByEventID(eventId);
        assertFalse(rsvps.isEmpty());
        assertEquals(userId, rsvps.get(0).getUserId());
    }

    @Test
    void updateStatus_updatesRSVPStatusSuccessfully() {
        rsvpService.createRSVP(eventId, userId);
        boolean statusUpdated = rsvpService.updateStatus(eventId, userId, 1);
        assertTrue(statusUpdated);
    }

    @Test
    void createRSVP_createsNewRSVP() {
        boolean success = rsvpService.createRSVP(eventId, userId);
        assertTrue(success);
        List<RSVPDTO> rsvps = rsvpService.fetchAllByUserID(userId);
        assertEquals(1, rsvps.size());
    }
}




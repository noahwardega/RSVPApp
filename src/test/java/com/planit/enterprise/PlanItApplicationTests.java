package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.Event;
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
    private Event testEvent;
    private int userId;
    private int eventId;

    @BeforeEach
    void setup() {
        userId = userService.registerUser("Test", "Test", "Test@email.com");
        testUser = userService.getUserById(userId);
        assertNotNull(testUser);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Test Event");
        eventDTO.setLocation("New York");
        eventDTO.setDate(LocalDateTime.of(2025, 4, 1, 10, 0));
        eventDTO.setHostId(testUser.getId());

        testEvent = eventService.createEvent(eventDTO, testUser);
        assertNotNull(testEvent);
        assertTrue(testEvent.getId() > 0);

        eventId = testEvent.getId();
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
        User currentUser = new User();
        currentUser.setId(1);
        currentUser.setFName("John");
        currentUser.setLName("Doe");
        currentUser.setEmail("john.doe@example.com");

        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Conference");
        eventDTO.setLocation("Los Angeles");
        eventDTO.setDate(LocalDateTime.of(2025, 6, 10, 9, 0));

        Event event = eventService.createEvent(eventDTO, currentUser);

        assertNotNull(event);
        assertTrue(event.getId() > 0);
        assertEquals("Conference", event.getName());
        assertEquals("Los Angeles", event.getLocation());
        assertEquals(LocalDateTime.of(2025, 6, 10, 9, 0), event.getDate());
        assertEquals(currentUser.getId(), event.getHost().getId());
    }


    @Test
    void fetchAllByUserID_returnsRSVPs() {
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        rsvpService.createOrUpdateRSVP(user, event, "Yes");

        List<RSVPDTO> rsvps = rsvpService.getRSVPsByUser(user);
        assertFalse(rsvps.isEmpty());
        assertEquals(eventId, rsvps.get(0).getEventId());
        assertEquals(userId, rsvps.get(0).getUserId());

        assertEquals(1, rsvps.get(0).getRsvpStatus());

        assertEquals("Yes", rsvps.get(0).getRsvpStatusAsString());
    }

    @Test
    void fetchAllByEventID_returnsRSVPs() {
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        rsvpService.createOrUpdateRSVP(user, event, "Yes");

        List<RSVPDTO> rsvps = rsvpService.getRSVPsByEvent(event);
        assertFalse(rsvps.isEmpty());
        assertEquals(userId, rsvps.get(0).getUserId());
        assertEquals(eventId, rsvps.get(0).getEventId());

        assertEquals(1, rsvps.get(0).getRsvpStatus());

        assertEquals("Yes", rsvps.get(0).getRsvpStatusAsString());
    }


    @Test
    void createRSVP_createsNewRSVP() {
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        rsvpService.createOrUpdateRSVP(user, event, "Yes");

        List<RSVPDTO> rsvps = rsvpService.getRSVPsByUser(user);
        assertEquals(1, rsvps.size());

        assertEquals(1, rsvps.get(0).getRsvpStatus());

        assertEquals("Yes", rsvps.get(0).getRsvpStatusAsString());
    }
}







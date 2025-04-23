package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.repository.RSVPRepository;
import com.planit.enterprise.repository.UserRepository;
import com.planit.enterprise.service.interfaces.IEventService;
import com.planit.enterprise.service.interfaces.IRSVPService;
import com.planit.enterprise.service.interfaces.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EventController {

    @Autowired
    private IEventService eventService;

    @Autowired
    private IRSVPService rsvpService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RSVPRepository rsvpRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/start")
    public String showHomepage(Model model) {
        User currentUser = userService.getCurrentUser(session);

        if (currentUser != null) {
            List<Event> userEvents = eventService.getEventsByHost(currentUser);

            List<RSVPDTO> invitedRSVPs = rsvpService.getRSVPsByUser(currentUser);

            List<Event> invitedEvents = invitedRSVPs.stream()
                    .map(rsvp -> eventService.getEventById(rsvp.getEventId()))
                    .collect(Collectors.toList());

            model.addAttribute("currentUser", currentUser);

            model.addAttribute("yourEvents", userEvents);
            model.addAttribute("invitedEvents", invitedEvents);
            model.addAttribute("user", currentUser);
        } else {
            return "redirect:/signIn";
        }

        return "start";
    }

    @GetMapping("/createEvent")
    public String showCreateEventForm(Model model) {
        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("eventDTO", new EventDTO());

        List<User> users = userRepository.findAll();
        users.removeIf(user -> user.getId() == currentUser.getId());

        model.addAttribute("allUsers", users);
        return "createEvent";
    }

    @PostMapping("/createEvent")
    public String createEvent(@ModelAttribute EventDTO eventDTO, @RequestParam List<Integer> attendeeIds) {

        User currentUser = userService.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setHost(currentUser);

        Event savedEvent = eventService.saveEvent(event);

        for (Integer userId : attendeeIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: ID " + userId));

            String status = "Unknown";
            RSVP rsvp = new RSVP(user, savedEvent, status);
            rsvpRepository.save(rsvp);
        }

        return "redirect:/start";
    }


    @PostMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable("id") int eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/start";
    }

}



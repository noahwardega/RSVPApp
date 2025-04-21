package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
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
    private HttpSession session;

    @GetMapping("/start")
    public String showHomepage(Model model) {
        User currentUser = userService.getCurrentUser(session);

        if (currentUser != null) {
            // Fetch events the user is hosting
            List<Event> userEvents = eventService.getEventsByHost(currentUser);

            // Fetch RSVPs for events the user is invited to
            List<RSVPDTO> invitedRSVPs = rsvpService.getRSVPsByUser(currentUser);

            // Fetch the actual events based on RSVP event IDs
            List<Event> invitedEvents = invitedRSVPs.stream()
                    .map(rsvp -> eventService.getEventById(rsvp.getEventId())) // Assuming this method exists
                    .collect(Collectors.toList());

            model.addAttribute("yourEvents", userEvents);
            model.addAttribute("invitedEvents", invitedEvents);
            model.addAttribute("user", currentUser);
        } else {
            return "redirect:/signIn";
        }

        return "start";
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        return "createEvent";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute EventDTO eventDTO) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {

            eventService.createEvent(eventDTO, currentUser);
            return "redirect:/start";
        }

        return "redirect:/signIn";
    }




}



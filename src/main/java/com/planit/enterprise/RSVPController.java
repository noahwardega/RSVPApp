package com.planit.enterprise;

import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.service.interfaces.IRSVPService;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rsvps")
public class RSVPController {

    @Autowired
    private IRSVPService rsvpService;

    @Autowired
    private IEventService eventService;

    @Autowired
    private HttpSession session;

    @GetMapping("/view/{eventId}")
    public String viewEvent(@PathVariable("eventId") int eventId, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            Event event = eventService.getEventById(eventId);
            Optional<Event> optionalEvent = Optional.ofNullable(event);

            if (optionalEvent.isPresent()) {
                model.addAttribute("event", optionalEvent.get());

                List<RSVPDTO> rsvps = rsvpService.getRSVPsByEvent(optionalEvent.get());
                model.addAttribute("rsvps", rsvps);

                RSVPDTO rsvpStatus = rsvps.stream()
                        .filter(rsvp -> rsvp.getUserId() == currentUser.getId())
                        .findFirst()
                        .orElse(null);
                model.addAttribute("rsvpStatus", rsvpStatus != null ? rsvpStatus.getRsvpStatusAsString() : "Not RSVPed");
            }
        } else {
            return "redirect:/signIn";
        }

        return "viewEvent";
    }
}









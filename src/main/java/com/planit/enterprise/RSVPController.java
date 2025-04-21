package com.planit.enterprise;

import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.service.interfaces.IEventService;
import com.planit.enterprise.service.interfaces.IRSVPService;
import com.planit.enterprise.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rsvp")
public class RSVPController {

    @Autowired
    private IRSVPService rsvpService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IEventService eventService;



    @GetMapping("/event/{eventId}")
    public String getRSVPsForEvent(@PathVariable int eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        List<RSVP> rsvps = rsvpService.getRSVPsByEvent(event);

        model.addAttribute("event", event);
        model.addAttribute("rsvps", rsvps);
        return "eventRsvps";
    }


    @PostMapping
    public String submitRSVP(@RequestParam int userId,
                             @RequestParam int eventId,
                             @RequestParam String status) {
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        rsvpService.createOrUpdateRSVP(user, event, status);

        return "redirect:/event/" + eventId;
    }
}




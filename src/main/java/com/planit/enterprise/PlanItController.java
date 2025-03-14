package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PlanItController {
    /**
     * Handle the root (/) endpoint and return a start page
     */

    @RequestMapping("/")
    public String signIn(UserDTO userDTO){
        return "signIn";
    }

    @RequestMapping("/signIn")
    public String start(UserDTO userDTO){
        return "start";
    }

    @RequestMapping("/saveEvent")
    public String index(Model model){
          EventDTO event = new EventDTO();
          event.setName("Test Event");
          event.setDate("1/1/2025");
          model.addAttribute(event);
        return "start";
    }

    /* Sends user to createEvent page when Create Event button is pressed */
    @RequestMapping("/createEvent")
    public String createEvent(EventDTO event){
        return "createEvent";
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
}

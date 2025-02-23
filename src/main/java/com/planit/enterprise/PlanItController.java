package com.planit.enterprise;

import com.planit.enterprise.dto.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlanItController {
    /**
     * Handle the root (/) endpoint and return a start page
     * @return
     */
    @RequestMapping("/")
    public String index(){
        /*
          testing event description stuff from video
          Event event = new Event();
          event.setEventDescription("This is the event description");
          String desc = event.getEventDescription();
         */
        return "start";
    }
}

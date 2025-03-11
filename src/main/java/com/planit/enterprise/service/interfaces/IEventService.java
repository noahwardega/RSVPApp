package com.planit.enterprise.service.interfaces;

import java.util.List;
import com.planit.enterprise.dto.EventDTO;

public interface IEventService {
    //get all events
    List<EventDTO> getAllEvents();

    //update an event
    void updateEvent(int eventId, EventDTO event);

    //delete an event
    void deleteEvent(int eventId);

    /**
     * Fetch single object of class EventDTO given ID.
     * @param id unique identifier for event.
     * @return the matching user, or null if no matches found.
     */
    EventDTO fetchEventByID(int id);

    /**
     * Posts new event to database
     * @param name name of event
     * @param date date event takes place
     * @param location location event takes place
     * @return the event ID for newly created event, -1 on failure
     */
    int addEvent(String name, String date, String location);
}

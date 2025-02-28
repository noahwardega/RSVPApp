package com.planit.enterprise.service.interfaces;

import java.util.List;
import com.planit.enterprise.dto.EventDTO;

public interface IEventService {
    //create event
    void createEvent(EventDTO event);

    //get all events
    List<EventDTO> getAllEvents();

    //get event by id
    EventDTO getEventById(int id);

    //update an event
    void updateEvent(int eventId, EventDTO event);

    //delete an event
    void deleteEvent(int eventId);
}

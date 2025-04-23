package com.planit.enterprise.service.interfaces;


import java.util.List;
import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;

public interface IEventService {

    Event createEvent(EventDTO eventDTO, User host);

    Event getEventById(int id);

    List<Event> getEventsByHost(User host);

    Event saveEvent(Event event);

    void deleteEvent(int eventId);

}


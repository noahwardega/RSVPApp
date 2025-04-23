package com.planit.enterprise.service.interfaces;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;

public interface IEventService {
    Event createEvent(EventDTO eventDTO, User host);
    List<EventDTO> getEventsByUser(User user);
    Event getEventById(int id);
    List<Event> getEventsInvitedTo(User user);
    List<Event> getEventsByHost(User host);

    Event saveEvent(Event event);

    void deleteEvent(int eventId);

}


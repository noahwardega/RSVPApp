package com.planit.enterprise.service.interfaces;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;

public interface IEventService {
    EventDTO createEvent(EventDTO eventDTO, User host);
    List<EventDTO> getEventsByUser(User user);
    Event getEventById(int id);
    List<Event> getEventsInvitedTo(User user);
    List<Event> getEventsByHost(User host);

    EventDTO saveEvent(EventDTO eventDTO);
}


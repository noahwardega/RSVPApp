package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.repository.EventRepository;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getEventsByHost(User host) {
        return eventRepository.findByHost(host);
    }

    public Event createEvent(EventDTO eventDTO, User host) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setLocation(eventDTO.getLocation());
        event.setDate(eventDTO.getDate());
        event.setHost(host);
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event getEventById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<Event> getEventsInvitedTo(User user) {
        return eventRepository.findByInvitedUsersContaining(user);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(int eventId) {
        eventRepository.deleteById(eventId);

    }

}





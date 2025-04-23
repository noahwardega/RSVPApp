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

    /**
     * Retrieves all events hosted by a specific user.
     *
     * @param host the user hosting the events
     * @return a list of events hosted by the user
     */
    @Override
    public List<Event> getEventsByHost(User host) {
        return eventRepository.findByHost(host);
    }

    /**
     * Creates a new event with the given details and host.
     *
     * @param eventDTO the data transfer object containing event details
     * @param host the user who is creating/hosting the event
     * @return the newly created Event entity
     */
    public Event createEvent(EventDTO eventDTO, User host) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setLocation(eventDTO.getLocation());
        event.setDate(eventDTO.getDate());
        event.setHost(host);
        eventRepository.save(event);
        return event;
    }

    /**
     * Retrieves a specific event by its ID.
     *
     * @param id the ID of the event
     * @return the Event entity with the given ID
     * @throws RuntimeException if the event is not found
     */
    @Override
    public Event getEventById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    /**
     * Saves or updates an event entity.
     *
     * @param event the Event entity to be saved or updated
     * @return the saved or updated Event entity
     */
    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Deletes an event by its ID.
     *
     * @param eventId the ID of the event to be deleted
     */
    @Override
    public void deleteEvent(int eventId) {
        eventRepository.deleteById(eventId);
    }

}






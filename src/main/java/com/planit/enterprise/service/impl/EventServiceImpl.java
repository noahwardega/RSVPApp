package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.repository.EventRepository;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventDTO> fetchAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> new EventDTO(event.getId(), event.getName(), event.getDate(), event.getLocation()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateEvent(int eventId, EventDTO eventDTO) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            event.setName(eventDTO.getName());
            event.setDate(eventDTO.getDate());
            event.setLocation(eventDTO.getLocation());
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEvent(int eventId) {
        eventRepository.deleteById(eventId);
        return true;
    }

    @Override
    public EventDTO fetchEventByID(int id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            return new EventDTO(event.getId(), event.getName(), event.getDate(), event.getLocation());
        }
        return null; // Return null if event is not found
    }

    @Override
    public int createEvent(String name, String date, String location) {
        Event event = new Event();
        event.setName(name);
        event.setDate(date);
        event.setLocation(location);
        eventRepository.save(event);
        return event.getId(); // Return the event ID after saving
    }
}


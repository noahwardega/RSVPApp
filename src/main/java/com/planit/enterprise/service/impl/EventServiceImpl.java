package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.repository.EventRepository;
import com.planit.enterprise.repository.UserRepository;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Event> getEventsByHost(User host) {
        return eventRepository.findByHost(host);
    }

    public EventDTO createEvent(EventDTO eventDTO, User host) {
        // Create a new event
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setLocation(eventDTO.getLocation());
        event.setDate(eventDTO.getDate());
        event.setHost(host); // Set the current user as the host

        // Save the event
        eventRepository.save(event);

        // Convert the event to DTO
        List<Integer> attendeeIds = event.getInvitedUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getLocation(),
                event.getDate(),
                host.getId(),
                attendeeIds
        );
    }


    public List<EventDTO> getEventsByUser(User user) {
        List<Event> events = eventRepository.findByHost(user);
        return events.stream().map(event -> new EventDTO(event.getId(), event.getName(), event.getLocation(), event.getDate(), event.getHost().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Event getEventById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<Event> getEventsInvitedTo(User user) {
        return eventRepository.findByInvitedUsersContaining(user);
    }

    @Override
    public EventDTO saveEvent(EventDTO eventDTO) {
        Event event = dtoToEvent(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return eventToDTO(savedEvent);
    }

    private EventDTO eventToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setLocation(event.getLocation());
        dto.setDate(event.getDate());

        dto.setHostId(event.getHost() != null ? event.getHost().getId() : 0);

        List<Integer> attendeeIds = event.getInvitedUsers().stream()
                .map(User::getId)
                .toList();

        dto.setAttendeeIds(attendeeIds);

        return dto;
    }

    private Event dtoToEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());

        User host = userRepository.findById(eventDTO.getHostId()).orElse(null);
        event.setHost(host);

        Set<User> invitedUsers = new HashSet<>();
        if (eventDTO.getAttendeeIds() != null) {
            for (Integer userId : eventDTO.getAttendeeIds()) {
                userRepository.findById(userId).ifPresent(invitedUsers::add);
            }
        }
        event.setInvitedUsers(invitedUsers);

        return event;
    }



}





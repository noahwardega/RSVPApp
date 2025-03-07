package com.planit.enterprise.service.stubs;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.service.interfaces.IEventService;

import java.util.List;

public class EventServiceStub implements IEventService {
    @Override
    public void createEvent(EventDTO event) {

    }

    @Override
    public List<EventDTO> getAllEvents() {
        return List.of();
    }

    @Override
    public EventDTO getEventById(int id) {
        return null;
    }

    @Override
    public void updateEvent(int eventId, EventDTO event) {

    }

    @Override
    public void deleteEvent(int eventId) {

    }
}

package com.planit.enterprise.service.stubs;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventServiceStub implements IEventService {

    @Override
    public List<EventDTO> fetchAllEvents() {
        return List.of();
    }

    @Override
    public boolean updateEvent(int eventId, EventDTO event) {
        return false;
    }

    @Override
    public boolean deleteEvent(int eventId) {
        return false;
    }

    @Override
    public EventDTO fetchEventByID(int id) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Debbie");
        return eventDTO;
    }

    @Override
    public int createEvent(String name, String date, String location) {
        return 1;
    }
}

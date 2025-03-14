package com.planit.enterprise.service.stubs;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventServiceStub implements IEventService {

    @Override
    public List<EventDTO> getAllEvents() {
        return List.of();
    }

    @Override
    public void updateEvent(int eventId, EventDTO event) {

    }

    @Override
    public void deleteEvent(int eventId) {

    }

    @Override
    public EventDTO fetchEventByID(int id) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Debbie");
        return eventDTO;
    }

    @Override
    public int addEvent(String name, String date, String location) {
        return 1;
    }
}

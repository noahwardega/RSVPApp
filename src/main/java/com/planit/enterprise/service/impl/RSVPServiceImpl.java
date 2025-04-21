package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.repository.EventRepository;
import com.planit.enterprise.repository.RSVPRepository;
import com.planit.enterprise.repository.UserRepository;
import com.planit.enterprise.service.interfaces.IRSVPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RSVPServiceImpl implements IRSVPService {

    @Autowired
    private RSVPRepository rsvpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void createOrUpdateRSVP(User user, Event event, String status) {
        RSVP existingRSVP = rsvpRepository.findByUserAndEvent(user, event)
                .orElse(null);

        if (existingRSVP == null) {
            RSVP newRSVP = new RSVP();
            newRSVP.setUser(user);
            newRSVP.setEvent(event);
            newRSVP.setStatus(status);
            rsvpRepository.save(newRSVP);
        } else {
            existingRSVP.setStatus(status);
            rsvpRepository.save(existingRSVP);
        }
    }



    @Override
    public List<RSVPDTO> getRSVPsByEvent(Event event) {
        List<RSVP> rsvps = rsvpRepository.findByEvent(event);

        return rsvps.stream()
                .map(rsvp -> new RSVPDTO(
                        rsvp.getId(),
                        rsvp.getUser().getId(),
                        rsvp.getEvent().getId(),
                        rsvp.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RSVPDTO> getRSVPsByUser(User user) {
        List<RSVP> rsvps = rsvpRepository.findByUser(user);

        return rsvps.stream()
                .map(rsvp -> new RSVPDTO(
                        rsvp.getId(),
                        rsvp.getUser().getId(),
                        rsvp.getEvent().getId(),
                        rsvp.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public String getRSVPStatus(Event event, User user) {
        RSVP rsvp = rsvpRepository.findByUserAndEvent(user, event)
                .orElseThrow(() -> new RuntimeException("RSVP not found"));
        return rsvp.getStatus();
    }

    @Override
    public void updateRSVPStatus(Event event, User user, String status) {
        RSVP rsvp = rsvpRepository.findByUserAndEvent(user, event)
                .orElseThrow(() -> new RuntimeException("RSVP not found"));

        rsvp.setStatus(status);
        rsvpRepository.save(rsvp);
    }
}







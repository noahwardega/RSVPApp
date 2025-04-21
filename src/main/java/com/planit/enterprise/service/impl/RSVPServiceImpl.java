package com.planit.enterprise.service.impl;

import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.repository.RSVPRepository;
import com.planit.enterprise.service.interfaces.IRSVPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RSVPServiceImpl implements IRSVPService {

    @Autowired
    private RSVPRepository rsvpRepository;

    @Override
    public List<RSVP> getRSVPsByEvent(Event event) {
        return rsvpRepository.findByEvent(event);
    }

    @Override
    public List<RSVPDTO> getRSVPsByUser(User user) {
        List<RSVP> rsvps = rsvpRepository.findByUser(user);
        return rsvps.stream()
                .map(r -> new RSVPDTO(r.getId(), r.getUserId(), r.getEventId(), r.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void createOrUpdateRSVP(User user, Event event, String status) {
        Optional<RSVP> existing = rsvpRepository.findByUserAndEvent(user, event);

        RSVP rsvp = existing.orElse(new RSVP());
        rsvp.setUser(user);
        rsvp.setEvent(event);
        rsvp.setStatus(status);

        rsvpRepository.save(rsvp);
    }


}




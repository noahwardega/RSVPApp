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

    /**
     * Creates a new RSVP or updates an existing one for a given user and event.
     *
     * @param user the user submitting the RSVP
     * @param event the event the RSVP is for
     * @param status the RSVP status (e.g., "Yes", "No", "Maybe")
     */
    @Override
    public void createOrUpdateRSVP(User user, Event event, String status) {
        Optional<RSVP> optionalRSVP = rsvpRepository.findByUserAndEvent(user, event);

        if (optionalRSVP.isPresent()) {
            RSVP existingRSVP = optionalRSVP.get();
            existingRSVP.setStatus(status);
            rsvpRepository.save(existingRSVP);
        } else {
            RSVP newRSVP = new RSVP();
            newRSVP.setUser(user);
            newRSVP.setEvent(event);
            newRSVP.setStatus(status);
            rsvpRepository.save(newRSVP);
        }
    }

    /**
     * Retrieves all RSVPs for a specific event and maps them to RSVPDTOs.
     *
     * @param event the event to fetch RSVPs for
     * @return a list of RSVPDTOs representing RSVPs to the event
     */
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

    /**
     * Retrieves all RSVPs made by a specific user and maps them to RSVPDTOs.
     *
     * @param user the user whose RSVPs are being fetched
     * @return a list of RSVPDTOs representing the user's RSVPs
     */
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

}







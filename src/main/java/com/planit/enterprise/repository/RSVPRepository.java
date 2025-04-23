package com.planit.enterprise.repository;

import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.RSVP;
import com.planit.enterprise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RSVPRepository extends JpaRepository<RSVP, Integer> {

    List<RSVP> findByEvent(Event event);
    List<RSVP> findByUser(User user);
    Optional<RSVP> findByUserAndEvent(User user, Event event);

}

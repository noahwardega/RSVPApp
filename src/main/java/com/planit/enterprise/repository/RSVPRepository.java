package com.planit.enterprise.repository;

import com.planit.enterprise.entity.RSVP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RSVPRepository extends JpaRepository<RSVP, Integer> {

    List<RSVP> findByUserId(int userId);

    List<RSVP> findByEventId(int eventId);

    RSVP findByUserIdAndEventId(int userId, int eventId);
}

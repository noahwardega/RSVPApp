package com.planit.enterprise.repository;
import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByHost(User host);
    List<Event> findByInvitedUsersContaining(User user);
    void deleteById(int id);  // Deletes the event by its ID



}

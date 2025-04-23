package com.planit.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;  // The user who created the event

    @OneToMany(mappedBy = "event")
    private Set<RSVP> rsvps;

    @ManyToMany
    @JoinTable(
            name = "rsvps",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> invitedUsers = new HashSet<>();

    public Event(String name, LocalDateTime date, String location, User host) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.host = host;
    }

    public List<Integer> getInviteeIds() {
        return invitedUsers.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }


}


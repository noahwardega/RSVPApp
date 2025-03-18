package com.planit.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor  // Lombok generates the default constructor
@AllArgsConstructor  // Lombok generates a constructor with all fields
@Data
@Table(name = "rsvps")
public class RSVP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String rsvpStatus; // "Going", "Maybe", "Not Going"
}
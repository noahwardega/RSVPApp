package com.planit.enterprise.dto;

import lombok.Data;

public @Data class Event {
    private int eventId;
    public String eventName;
    public String eventTime;
    public String eventLocation;
    public String eventDescription;
}

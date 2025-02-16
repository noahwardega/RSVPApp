# PlanIt Design Document

## Introduction

Our application allows users to create and manage events.  Users will be able to see upcoming events that they have RSVP’d to from the home page, and they can select events from the list to see further details about each event.  Users can create events, set details for the events, and invite others through PlanIt.  Event organizers can also see the status of those who have been invited to the event to know who will be attending.  PlanIt allows users to “liftoff” their event planning skills!  

## Storyboard

## Functional Requirements

Functional Requirements #1
- **As a** User
- **I want** To Create an Event
- **So that I can** Invite Users, Set Event details, invite people
  - Example #1
    - **Given** a user who wants to create an event
    - **When** user Clicks to create an event 
    - **Then** user is taken to Create Event Page
  - Example #2
    - **Given** a user who wants to create an event
    - **When** a user is on create event page and adds event details
    - **Then** correct details of the event display where user sees events
  - Example #3
    - **Given** a user who wants to create an event
    - **When** a user is creating an event and adds people to invite list
    - **Then** other users that are invited to event can see event details

Functional Requirements #2
- **As a** User
- **I want** to Manage My Events
- **So that I can** RSVP and view upcoming events
  - Example #1
    - **Given** a user wants to view their events
    - **When** a user is on home page, Sees upcoming events, and Clicks on event
    - **Then** event Displays with correct information of the selected event
  - Example #2
    - **Given** a user wants to RSVP to an event
    - **When** a user is on home page, Sees upcoming events, Clicks on event, and RSVPS to event
    - **Then** event shows the status of their RSVP

### Class Diagram

### JSON Schema
>{
>  "EventDTO": {
>    "properties": {
>    "type": "object",
>        "id": { "type": "integer" },
>        "name": { "type": "string" },
>        "date": { "type": "string" },
>        "location": { "type": "string" },
>        "attendees": {"type": "array",
>          "items": { "$ref": "#/RSVPDTO" }
>      }
>    },
>    "required": ["id", "name", "date", "location"]
>  },
>
>  "UserDTO": {
>    "type": "object",
>    "properties": {
>      "id": { "type": "integer" },
>      "name": { "type": "string" },
>      "email": { "type": "string" },
>      "rsvpEvents": {"type": "array",
>        "items": { "$ref": "#/RSVPDTO" }
>      }
>    },
>    "required": ["id", "name", "email"]
>  },
>  "RSVPDTO": {
>    "type": "object",
>    "properties": {
>      "eventId": { "type": "integer" },
>      "userId": { "type": "integer" },
>      "rsvpStatus": { "type": "string" }
>    },
>    "required": ["eventId", "userId", "rsvpStatus"]
>  }
>}

### Scrum Roles

UI - Emma Danner, Calvin Yeboah
Business Logic and Persistence - Noah Wardega
Product Owner/Scrum Master - Dominic Scott

## Project Link

https://github.com/dominichscott/EnterpriseAppDevGroup3

*All Milestones and Projects are set up within this GitHub repository.*

*As a group, we have decided to meet Mondays at 7:30PM using teams.*
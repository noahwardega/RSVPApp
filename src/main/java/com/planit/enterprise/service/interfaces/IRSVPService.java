package com.planit.enterprise.service.interfaces;

import com.planit.enterprise.dto.RSVPDTO;

import java.util.List;

public interface IRSVPService {
    /**
     * Retrieves a list of RSVPs that have the entered UserID
     * @param id the UserID to query with
     * @return the list of RSVPs, list will be empty if none are found
     */
    List<RSVPDTO> fetchAllByUserID(int id);

    /**
     * Retrieves a list of RSVPs that have the entered EventID
     * @param id the EventID to query with
     * @return the list of RSVPs, list will be empty if none are found
     */
    List<RSVPDTO> fetchAllByEventID(int id);

    /**
     * Alters the status of a specified RSVP
     * @param eventId ID of the event
     * @param userId ID of the user invited to the event
     * @param status new status of RSVP, 0 is undecided, 1 is yes, 2 is no
     * @return boolean indicating change was successful, will return true even
     *  if status was previously the same
     */
    Boolean updateStatus(int eventId, int userId, int status);

    /**
     * Adds additional RSVP for a specified user and event, status defaults to 0
     * @param eventId ID of the event
     * @param userId ID of the invited user
     * @return true if data was added successfully
     */
    Boolean createRSVP(int eventId, int userId);
}

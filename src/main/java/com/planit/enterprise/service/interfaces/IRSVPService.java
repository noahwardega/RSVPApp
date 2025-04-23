package com.planit.enterprise.service.interfaces;

import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.entity.Event;
import com.planit.enterprise.entity.User;
import java.util.List;

public interface IRSVPService {

    void createOrUpdateRSVP(User user, Event event, String status);

    List<RSVPDTO> getRSVPsByEvent(Event event);

    List<RSVPDTO> getRSVPsByUser(User user);

}



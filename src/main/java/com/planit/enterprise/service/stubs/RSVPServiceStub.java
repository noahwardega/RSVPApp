package com.planit.enterprise.service.stubs;

import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.service.interfaces.IRSVPService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RSVPServiceStub implements IRSVPService {
    @Override
    public List<RSVPDTO> fetchAllByUserID(int id) {
        return List.of(new RSVPDTO(0, 0, ""));
    }

    @Override
    public List<RSVPDTO> fetchAllByEventID(int id) {
        return List.of(new RSVPDTO(0, 0, ""));
    }

    @Override
    public Boolean updateStatus(int eventId, int userId, int status) {
        return false;
    }

    @Override
    public Boolean createRSVP(int eventId, int userId) {
        return false;
    }
}

package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlanItApplicationTests {

    @Autowired
    private IEventService eventService;
    private EventDTO event;

    @Test
    void contextLoads() {
    }

}

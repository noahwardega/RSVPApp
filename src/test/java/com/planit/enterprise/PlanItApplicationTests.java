package com.planit.enterprise;

import com.planit.enterprise.dto.EventDTO;
import com.planit.enterprise.dto.RSVPDTO;
import com.planit.enterprise.dto.UserDTO;
import com.planit.enterprise.service.interfaces.IEventService;
import com.planit.enterprise.service.interfaces.IRSVPService;
import com.planit.enterprise.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PlanItApplicationTests {

    @Autowired
    private IUserService userService;
    private UserDTO user;
    private Boolean check1;
    private Boolean check2;
    private int checkId;
    @Autowired
    private IRSVPService rsvpService;
    List<RSVPDTO> rsvpList;
    Boolean statusChangeSuccess;
    Boolean addRSVPSuccess;
    @Autowired
    private IEventService eventService;
    private EventDTO event;

    @Test
    void contextLoads() {
    }

    @Test
    void fetchUserByID_returnsDebbieForID0()
    {
        givenUserDataIsAvailable();
        whenSearchUserWithID0();
        thenReturnOneUserWithNameDebbie();
    }

    @Test
    void fetchUserByEmail_returnsDebbieForEmail()
    {
        givenUserDataIsAvailable();
        whenSearchUserWithEmailDebbie();
        thenReturnOneUserWithNameDebbie();
    }

    // Debbie's email returns true, gibberish returns false
    @Test
    void doesEmailExist_check2Emails()
    {
        givenUserDataIsAvailable();
        whenCheckFor2EmailsExist();
        thenReturnOneUserEmailExistsAndOtherDoesNot();
    }

    @Test
    void registerUser_successfullyCreateBrady()
    {
        givenUserDataIsAvailable();
        whenAddUserBrady();
        thenReturnNewUserIDAbove0();
    }

    @Test
    void fetchAllByUserID_returnsOneEvent()
    {
        givenRSVPDataIsAvailable();
        whenSearchAllRSVPWithUserID0();
        thenReturnListWithOneItemAndNameDebbie();
    }

    @Test
    void fetchAllByEventID_returnsOneEvent()
    {
        givenRSVPDataIsAvailable();
        whenSearchAllRSVPWithEventID0();
        thenReturnListWithOneItemAndNameTestEvent();
    }

    @Test
    void updateStatus_rsvpForTestEvent()
    {
        givenRSVPDataIsAvailable();
        whenUpdateRSVPStatusOfID0();
        thenReturnTrueIfChangeWasSuccessful();
    }

    @Test
    void createRSVP_AddDebbieToEventID1()
    {
        givenRSVPDataIsAvailable();
        whenAddRSVPEvent1();
        thenReturnTrueOnSuccessfulRSVPCreation();
    }

    @Test
    void fetchEventByID_returnsTestEventForID0()
    {
        givenEventDataIsAvailable();
        whenSearchEventWithID0();
        thenReturnOneEventWithNameTestEvent();
    }

    @Test
    void createEvent_createEventExample()
    {
        givenEventDataIsAvailable();
        whenAddEventExample();
        thenReturnNewEventIDAbove0();
    }

    private void whenAddEventExample() {
        checkId = eventService.createEvent("Example", "1/1/2000", "Cincinnati");
    }

    private void thenReturnNewEventIDAbove0() {
        assertTrue(checkId > 0);
    }

    private void whenSearchEventWithID0() {
        event = eventService.fetchEventByID(0);
    }

    private void thenReturnOneEventWithNameTestEvent() {
        assertEquals("Test Event", event.getName());
    }

    private void givenEventDataIsAvailable() {
    }

    private void whenAddRSVPEvent1() {
        addRSVPSuccess = rsvpService.createRSVP(1, 0);
    }

    private void thenReturnTrueOnSuccessfulRSVPCreation() {
        assertTrue(addRSVPSuccess);
    }

    private void whenUpdateRSVPStatusOfID0() {
        statusChangeSuccess = rsvpService.updateStatus(0, 0, 1);
    }

    private void thenReturnTrueIfChangeWasSuccessful() {
        assertTrue(statusChangeSuccess);
    }

    private void whenSearchAllRSVPWithEventID0() {
        rsvpList = rsvpService.fetchAllByEventID(0);
    }

    private void thenReturnListWithOneItemAndNameTestEvent() {
        assertEquals(1, rsvpList.size());
        assertEquals("Test Event", eventService.fetchEventByID(rsvpList.get(0).getEventId()).getName());
    }

    private void whenSearchAllRSVPWithUserID0() {
        rsvpList = rsvpService.fetchAllByUserID(0);
    }

    private void thenReturnListWithOneItemAndNameDebbie() {
        assertEquals(1, rsvpList.size());
        assertEquals("Debbie", userService.fetchUserByID(rsvpList.get(0).getUserId()).getFName());
    }

    private void givenRSVPDataIsAvailable() {
    }

    private void whenAddUserBrady() {
        checkId = userService.registerUser("Brady", "Test", "bradyTest@email.com");
    }

    private void thenReturnNewUserIDAbove0() {
        assertTrue((checkId > 0));
    }

    private void whenCheckFor2EmailsExist() {
        check1 = userService.doesEmailExist("debbie@gmail.com");
        check2 = userService.doesEmailExist("12345badEmail");
    }

    private void thenReturnOneUserEmailExistsAndOtherDoesNot() {
        assertEquals(true, check1);
        assertEquals(false, check2);
    }

    private void whenSearchUserWithEmailDebbie()
    {
        user = userService.fetchUserByEmail("debbie@email.com");
    }

    private void givenUserDataIsAvailable() {
    }

    private void whenSearchUserWithID0()
    {
        user = userService.fetchUserByID(0);
    }

    private void thenReturnOneUserWithNameDebbie()
    {
        String name = user.getFName();
        assertEquals("Debbie", name);
    }

}

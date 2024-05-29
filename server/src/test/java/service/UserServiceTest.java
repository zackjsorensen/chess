package service;

import Service.UserService;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService service;
    UserData george = new UserData("George", "1234", "George@test.com");
    UserData elena = new UserData("Elena", "password", "ElenaSanchez@test.com");
    @BeforeEach
    void setUp(){
        service = new UserService(new MemoryUserDAO());
        service.addUser(george);
    }


    @Test
    void getUserCleared() {

        service.addUser(elena);
        service.clearUsers();
        assertNull(service.getUser("George"));
    }

    @Test
    void addUser(){
        Assertions.assertEquals(service.getUser("George"), george);
        assertEquals(service.getUser("George").email(), "George@test.com");
    }

    @Test
    void addMultipleUsers(){
        service.addUser(elena);
        assertEquals(service.getUser("Elena"), elena);
        assertEquals(service.getUser("George"), george);
        assertNull(service.getUser(" y"));
    }


    @Test
    void getUser() {
    }

    @Test
    void testAddUser() {
    }

    @Test
    void clearUsers() {
    }

    @Test
    void verifyWrongUserName() {
        Assertions.assertFalse(service.verify(elena));
        // do I need to implement custom exceptions here?
    }

    @Test
    void verifyWrongPassword()  {
        UserData notGeorge = new UserData("George", "wrong", "George@test.com");
        Assertions.assertFalse(service.verify(notGeorge));
    }
}
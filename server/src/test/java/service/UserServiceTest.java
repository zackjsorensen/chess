package service;

import dataaccess.MemoryUserDAO;
import dataaccess.ResponseException;
import dataaccess.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService service;
    UserData george = new UserData("George", "1234", "George@test.com");
    UserData elena = new UserData("Elena", "password", "ElenaSanchez@test.com");
    @BeforeEach
    void setUp() throws ResponseException {
        service = new UserService(new SQLUserDAO());
        service.addUser(george);
    }


    @Test
    void getUserCleared() throws ResponseException {

        service.addUser(elena);
        service.clearUsers();
        assertNull(service.getUser("George"));
    }

    @Test
    void addUser() throws ResponseException {
        Assertions.assertEquals(service.getUser("George"), george);
        assertEquals(service.getUser("George").email(), "George@test.com");
    }

    @Test
    void addMultipleUsers() throws ResponseException {
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
    void verifyWrongUserName() throws ResponseException {
        Assertions.assertFalse(service.verify(elena));
        // do I need to implement custom exceptions here?
    }

    @Test
    void verifyWrongPassword() throws ResponseException {
        UserData notGeorge = new UserData("George", "wrong", "George@test.com");
        Assertions.assertFalse(service.verify(notGeorge));
    }
}
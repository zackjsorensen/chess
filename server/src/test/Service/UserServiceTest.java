package Service;

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
    }

    @Test
    void getUserCleared() {
        service.addUser(george);
        service.addUser(elena);
        service.clearUsers();
        assertNull(service.getUser("George"));
    }

    @Test
    void addUser(){
        service.addUser(george);
        Assertions.assertEquals(service.getUser("George"), george);
        assertEquals(service.getUser("George").email(), "George@test.com");
    }

    @Test
    void addMultipleUsers(){
        service.addUser(george);
        service.addUser(elena);
        assertEquals(service.getUser("Elena"), elena);
        assertEquals(service.getUser("George"), george);
        assertNull(service.getUser(" y"));
    }


}
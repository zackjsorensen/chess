package Service;

import dataaccess.MemoryAuthDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.AuthData;
import dataaccess.MemoryAuthDAO;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    AuthData joe = new AuthData("12348", "Joe");
    AuthService service;

    @BeforeEach
    public void setUp(){
        service = new AuthService(new MemoryAuthDAO());
    }

    @Test
    void getFromEmptyAuth() {
        Assertions.assertNull(service.getAuth("0000"));
    }

    @Test
    void getAuthBob(){
        String bobAuth = service.createAuth("Bob");
        assertEquals("Bob", service.getAuth(bobAuth).username());
    }
}
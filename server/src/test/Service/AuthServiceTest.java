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
    void clear(){
        String keithAuth = service.createAuth("Keith");
        String bobAuth = service.createAuth("Bob");
        service.clear();
        assertNull(service.getAuth(keithAuth));
        assertNull(service.getAuth(bobAuth));
    }

    @Test
    void getFromEmptyAuth() {
        Assertions.assertNull(service.getAuth("0000"));
    }

    @Test
    void getAuthBob(){
        String bobAuth = service.createAuth("Bob");
        assertEquals("Bob", service.getAuth(bobAuth).username());
        assertNotEquals("Bobo", service.getAuth(bobAuth).username());
    }

    @Test
    void deleteEmptyAuth(){
        service.deleteAuth("1234");
        assertNull(service.getAuth("1234"));
    }

    @Test
    void deleteAuth(){
        String joeAuth = service.createAuth("Joe");;
        assertNotNull(service.getAuth(joeAuth));
        service.deleteAuth(joeAuth);
        assertNull(service.getAuth(joeAuth));
    }
}
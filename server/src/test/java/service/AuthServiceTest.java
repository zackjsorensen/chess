package service;

import dataaccess.exception.ResponseException;
import dataaccess.memorydao.MemoryAuthDAO;
import dataaccess.sql.SQLAuthDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.AuthData;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    AuthData joe = new AuthData("12348", "Joe");
    AuthService service;

    @BeforeEach
    public void setUp(){
        service = new AuthService(new SQLAuthDAO());
    }

    @Test
    void clear() throws ResponseException {
        String keithAuth = service.createAuth("Keith").authToken();
        String bobAuth = service.createAuth("Bob").authToken();
        service.clear();
        assertNull(service.getAuth(keithAuth));
        assertNull(service.getAuth(bobAuth));
    }

    @Test
    void getFromEmptyAuth() throws ResponseException {
        assertNull(service.getAuth("0000"));
    }

    @Test
    void getAuthBob() throws ResponseException {
        String bobAuth = service.createAuth("Bob").authToken();
        assertEquals("Bob", service.getAuth(bobAuth).username());
        assertNotEquals("Bobo", service.getAuth(bobAuth).username());
    }

    @Test
    void deleteEmptyAuth() throws ResponseException {
        service.deleteAuth("1234");
        assertNull(service.getAuth("1234"));
    }

    @Test
    void deleteAuth() throws ResponseException {
        String joeAuth = service.createAuth("Joe").authToken();
        assertNotNull(service.getAuth(joeAuth));
        service.deleteAuth(joeAuth);
        assertNull(service.getAuth(joeAuth));
    }

    @Test
    void createTwo() throws ResponseException {
        String joeAuth = service.createAuth("Joe").authToken();
        String bobAuth = service.createAuth("Bob").authToken();
        assertEquals("Bob", service.getAuth(bobAuth).username());
        assertEquals("Joe", service.getAuth(joeAuth).username());
    }
}
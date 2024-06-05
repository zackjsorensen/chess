package dataaccess.sql;

import dataaccess.exception.ResponseException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {
    static SQLUserDAO database;

    @BeforeAll
    static void setup() throws ResponseException {
        database = new SQLUserDAO();
        database.clear();
    }

    @Test
    void addTestSuccess() throws ResponseException {
        database.add(new UserData("Ben", "Shhh", "n/a"));
        UserData user = (UserData) database.get("Ben");
        assertEquals("Ben", user.username());
        assertEquals("n/a", user.email());
    }

    @Test
    void addTestFail() throws ResponseException {
        database.add(new UserData("Ben", "Shhh", "n/a"));
        Exception e = assertThrows(ResponseException.class, ()-> database.add(new UserData("Ben", "Shhh", "n/a")));
    }

    @Test
    void clearTest() throws ResponseException {
        database.add(new UserData("Ben", "Shhh", "n/a"));
        database.add(new UserData("Ken", "Shhhhhh", "n/a"));
        database.clear();
        assertNull(database.get("Ben"));
        assertNull(database.get("Ken"));
    }

    @Test
    void getTest() throws ResponseException {
        database.add(new UserData("445", "Bo", "Hi"));
        UserData userData = (UserData) database.get("445");
        assertEquals("Bo", userData.password());
    }

    @Test
    void getTestFail() throws ResponseException {
        assertNull(database.get("123"));
    }


}
package dataaccess;

import dataaccess.exception.ResponseException;
import dataaccess.sql.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {
    static SQLUserDAO database;

    @BeforeAll
    static void Setup(){
        database = new SQLUserDAO();
    }

    @Test
    void addTestSuccess() throws ResponseException {
        database.clear();
        database.add(new UserData("Ben", "Shhh", "n/a"));
        UserData user = (UserData) database.get("Ben");
        assertEquals("Ben", user.username());
        assertEquals("n/a", user.email());
    }

    @Test
    void addTestFail() throws ResponseException {
        database.clear();
        database.add(new UserData("Ben", "Shhh", "n/a"));
        Exception e = assertThrows(ResponseException.class, ()-> database.add(new UserData("Ben", "Shhh", "n/a")));
    }

    @Test
    void clearTest() throws ResponseException {
        database.clear();
        database.add(new UserData("Ben", "Shhh", "n/a"));
        database.add(new UserData("Ken", "Shhhhhh", "n/a"));
        database.clear();
        assertNull(database.get("Ben"));
        assertNull(database.get("Ken"));
    }

}
package dataaccess.sql;

import dataaccess.exception.ResponseException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {
    static SQLAuthDAO database;

    @BeforeAll
    static void setup(){
        database = new SQLAuthDAO();
    }

    @BeforeEach
    void clearDB() throws ResponseException {
        database.clear();
    }

    @Test
    void addTestSuccess() throws ResponseException {
        database.add(new AuthData("adsf1234", "Ben"));
        AuthData auth =  database.get("adsf1234");
        assertEquals("Ben", auth.username());
    }

    @Test
    void addTestFail() throws ResponseException {
        database.add(new AuthData ("asdf1234", "Tom"));
        Exception e = assertThrows(ResponseException.class, ()-> database.add(new AuthData("asdf1234", "Tom")));
    }

    @Test
    void getTestSuccess() throws ResponseException {
        database.add(new AuthData ("asdf1234", "Tom"));
        assertEquals("Tom", database.get("asdf1234").username());
    }

    @Test
    void getTestFail() throws ResponseException {
        assertNull(database.get("0000"));
    }

    @Test
    void deleteSuccess() throws ResponseException {
        database.add(new AuthData("12345", "Ben"));
        database.delete("12345");
        assertNull(database.get("12345"));
    }

    @Test
    void deleteFail() throws ResponseException {
        database.add(new AuthData("12345", "Ben"));
        assertThrows(ResponseException.class, () -> database.delete("12355"));
    } // or should this be DataAccessException?


    @Test
    void clear() throws ResponseException {
        database.add(new AuthData("sfsdsf", "Bo"));
        database.add(new AuthData("sdf", "Hugh"));
        database.clear();
        assertNull(database.get("sdf"));
    }

}
package dataaccess.sql;

import dataaccess.exception.ResponseException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {
    static SQLAuthDAO database;

    @BeforeAll
    static void Setup(){
        database = new SQLAuthDAO();
    }

    @Test
    void addTestSuccess() throws ResponseException {
        database.clear();
        database.add(new AuthData("adsf1234", "Ben"));
        AuthData auth =  database.get("adsf1234");
        assertEquals("Ben", auth.username());
    }

}
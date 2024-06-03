package dataaccess;

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
    void addTest1() throws ResponseException {
        database.add(new UserData("Ben", "Shhh", "n/a"));
    }

}
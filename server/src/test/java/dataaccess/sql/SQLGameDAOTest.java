package dataaccess.sql;

import chess.ChessGame;
import dataaccess.exception.ResponseException;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLGameDAOTest {
    SQLGameDAO database;

    @BeforeEach
    void setUp() throws ResponseException {
        database = new SQLGameDAO();
        database.clear();
    }

    @Test
    void addSuccess() throws ResponseException {
        GameData dummy = new GameData(0, "Wyat", "Broc", "Test", new ChessGame());
        int id = database.add(dummy);
        assertEquals(dummy.whiteUsername(), database.get(id).whiteUsername());
        assertEquals(dummy.blackUsername(), database.get(id).blackUsername());
        assertEquals(dummy.gameName(), database.get(id).gameName());
    }
}
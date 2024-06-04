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
        GameData dummy = new GameData(12, "Wyat", "Broc", "Test", new ChessGame());
        database.add(dummy);
        assertEquals(dummy, database.get(12));
    }
}
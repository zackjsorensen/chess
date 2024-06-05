package dataaccess.sql;

import chess.ChessGame;
import dataaccess.exception.ResponseException;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SQLGameDAOTest {
    SQLGameDAO database;
    ChessGame blackGame = new ChessGame();
    ChessGame whiteGame = new ChessGame();

    @BeforeEach
    void setUp() throws ResponseException {
        database = new SQLGameDAO();
        database.clear();
        blackGame.setTeamTurn(ChessGame.TeamColor.BLACK);
        whiteGame.setTeamTurn(ChessGame.TeamColor.WHITE);
    }

    @Test
    void addSuccess() throws ResponseException {
        GameData dummy = new GameData(0, "Wyat", "Broc", "Test", new ChessGame());
        int id = database.add(dummy);
        assertEquals(dummy.whiteUsername(), database.get(id).whiteUsername());
        assertEquals(dummy.blackUsername(), database.get(id).blackUsername());
        assertEquals(dummy.gameName(), database.get(id).gameName());
    }

    @Test
    void addFail() throws ResponseException {
        GameData dummy = new GameData(0, "Wyat", "Broc", "Test", new ChessGame());
        UserData fake = new UserData("Hey!", "Youdon'tbelong", "here!");
        assertThrows(Exception.class, () -> database.add(fake));

    }

    @Test
    void getEmpty() throws ResponseException {
        assertNull(database.get(1));
    }

    @Test
    void getSuccess() throws ResponseException {
        GameData dummy = new GameData(0, "Wyat", "Broc", "Test", new ChessGame());
        int id = database.add(dummy);
        Object gameData = database.get(id);
        assertInstanceOf(GameData.class, gameData);
        assertInstanceOf(ChessGame.class, ((GameData) gameData).game());
    }

    @Test
    void updateGameStateTest() throws ResponseException {
        GameData dummy = new GameData(0, "Wyat", "Broc", "Test", blackGame);
        int id = database.add(dummy);
        database.updateGameState(id, new GameData(id, null, null, null, whiteGame));
        assertNotEquals(database.get(id).game().getTeamTurn(), ChessGame.TeamColor.BLACK);
    }

}
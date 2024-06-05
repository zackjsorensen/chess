package service;

import chess.ChessGame;
import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;
import dataaccess.memorydao.MemoryGameDAO;
import dataaccess.sql.SQLGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    SQLGameDAO dataAccess;
    GameService service;
    @BeforeEach
    void setup(){
       dataAccess = new SQLGameDAO();
       service = new GameService(dataAccess);
    }

    @Test
    void createAddGame() throws ResponseException {
        GameData newGame = new GameData(1234, null, null, "MyGame", new ChessGame());
        int id = dataAccess.add(newGame);
        assertEquals(dataAccess.get(id).gameName(), newGame.gameName());
    }

    @Test
    void createOneGame() throws DataAccessException {
        int id = service.createGame("Uno");
        assertEquals( dataAccess.get(id).gameName(), "Uno");
    }

    @Test
    void createMultipleGames() throws ResponseException {
        service.clear();
        service.createGame("uno");
        service.createGame("dos");
        service.createGame("tres");
        var resultList = service.listGames();
        assertEquals(3, resultList.length);
    }

    @Test
    void joinGameWhite() throws DataAccessException {
        service.clear();
        int id = service.createGame("Uno");
        service.joinGame(id, "Bub", "WHITE");
        GameData storedGame = dataAccess.get(id);
        assertEquals("Bub", storedGame.whiteUsername());
    }

    @Test
    void joinGameBlackTaken() {
        try {
            int id = service.createGame("Dos");
            service.joinGame(id, "Bub", "BLACK");
            DataAccessException e = assertThrows(DataAccessException.class, () -> service.joinGame(id, "Tim", "BLACK"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void listGames() {
    }


}
package service;

import chess.ChessGame;
import dataaccess.exception.DataAccessException;
import dataaccess.memorydao.MemoryGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    MemoryGameDAO dataAccess;
    GameService service;
    @BeforeEach
    void setup(){
       dataAccess = new MemoryGameDAO();
       service = new GameService(dataAccess);
    }

    @Test
    void createAddGame() {
        GameData newGame = new GameData(1234, null, null, "MyGame", new ChessGame());
        dataAccess.add(newGame);
        assertEquals(dataAccess.get(1234), newGame);
    }

    @Test
    void createOneGame() throws DataAccessException {
        service.createGame("Uno");
        assertEquals( dataAccess.get(10).gameName(), "Uno");
    }

    @Test
    void createMultipleGames()  {
        // I should do something about if they make a game with a duplicate name
        service.createGame("uno");
        service.createGame("dos");
        service.createGame("tres");
        var resultList = service.listGames();
        assertEquals(3, service.listGames().length);
    }

    @Test
    void joinGameWhite() throws DataAccessException {
        service.createGame("Uno");
        service.joinGame(10, "Bub", "WHITE");
        GameData storedGame = dataAccess.get(10);
        assertEquals(storedGame.whiteUsername(), "Bub");
    }

    @Test
    void joinGameBlackTaken() {
        try {
            service.createGame("Dos");
            service.joinGame(10, "Bub", "BLACK");
            DataAccessException e = assertThrows(DataAccessException.class, () -> service.joinGame(0, "Tim", "BLACK"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void listGames() {
    }


}
package Service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryGameDAO;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import javax.xml.crypto.Data;

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
        assertEquals( dataAccess.get(0).gameName(), "Uno");
    }

    @Test
    void createGameWithoutName() throws DataAccessException {
        DataAccessException e = assertThrows(DataAccessException.class, () -> service.createGame(null));
    }

    @Test
    void joinGameWhite() throws DataAccessException {
        service.createGame("Uno");
        service.joinGame(0, "Bub", "WHITE");
        GameData storedGame = dataAccess.get(0);
        assertEquals(storedGame.whiteUsername(), "Bub");
    }

    @Test
    void joinGameBlackTaken() {
        try {
            service.createGame("Dos");
            service.joinGame(0, "Bub", "BLACK");
            DataAccessException e = assertThrows(DataAccessException.class, () -> service.joinGame(0, "Tim", "BLACK"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void listGames() {
    }


}
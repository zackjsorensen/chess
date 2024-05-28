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
    @BeforeEach
    void setup(){
       dataAccess = new MemoryGameDAO();
    }

    @Test
    void createAddGame() {
        GameData newGame = new GameData(1234, null, null, "MyGame", new ChessGame());
        dataAccess.add(newGame);
        assertEquals(dataAccess.get(1234), newGame);
    }

    @Test
    void createOneGame() throws DataAccessException {
        GameService service = new GameService(dataAccess);
        service.createGame("Uno");
        assertEquals( dataAccess.get(0).gameName(), "Uno");
    }

    @Test
    void createGameWithoutName() throws DataAccessException {
        GameService service = new GameService(dataAccess);
        DataAccessException e = assertThrows(DataAccessException.class, () ->service.createGame(null));
    }

    @Test
    void listGames() {
    }


}
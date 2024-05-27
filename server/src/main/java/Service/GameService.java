package Service;

import chess.ChessGame;
import dataaccess.MemoryGameDAO;
import model.GameData;

import java.util.Collection;
import java.util.UUID;

public class GameService {
    public MemoryGameDAO dataAccess;

    public GameService(MemoryGameDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Collection listGames(){
        return null;
    }

    public int createGame(String name, String authToken){
        int gameID = dataAccess.size();
        dataAccess.add(new GameData(gameID, null, null, name, new ChessGame()));
        return gameID;
        // will need to handle failures...
    }



}

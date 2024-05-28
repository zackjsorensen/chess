package Service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.DataAccessInterface;
import dataaccess.MemoryGameDAO;
import model.GameData;

import java.util.ArrayList;

public class GameService {
    public MemoryGameDAO dataAccess;

    public GameService(MemoryGameDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public ArrayList listGames(){
        return dataAccess.listGames();
    }

    public int createGame(String name) throws DataAccessException {
        if (name == null){
            throw new DataAccessException("Game Name required");
        }
        int gameID = dataAccess.size();
        dataAccess.add(new GameData(gameID, null, null, name, new ChessGame()));
        return gameID;
        // will need to handle failures...
        // I guess I could check auth from here, but that seems complicated
    }

    public void joinGame(int gameID, String username, String color) throws DataAccessException {
        if (dataAccess.get(gameID) == null){
            throw new DataAccessException("Game does not exist");
        }
        if (isColorTaken(gameID, color)){
            throw new DataAccessException("Error: already taken");
        }
        GameData oldGameData = dataAccess.get(gameID);
        String newWhite = "", newBlack = "";
        if(color.equals("BLACK")){
            newWhite = oldGameData.whiteUsername();
            newBlack = username;
        } else if (color.equals("WHITE")){
            newWhite = username;
            newBlack = oldGameData.blackUsername();
        } // how to handle errors here?

        dataAccess.updateGame(gameID, new GameData(gameID, newWhite, newBlack,
                oldGameData.gameName(), oldGameData.game()));
    }

    public boolean isColorTaken(int gameID, String color) throws DataAccessException {
        if (color.equals("WHITE")){
            return dataAccess.get(gameID).whiteUsername() != null;
        } else if (color.equals("BLACK")) {
            return dataAccess.get(gameID).blackUsername() != null;
        } else {
            throw new DataAccessException("Bad color Request");
        }
    }

    



}

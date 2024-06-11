package service;

import chess.ChessGame;
import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;
import dataaccess.sql.SQLGameDAO;
import model.GameData;
import model.ListGamesGameUnit;

import java.util.ArrayList;

public class GameService {
    public SQLGameDAO dataAccess;
    public GameService(SQLGameDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public int createGame(String name) throws ResponseException {

        int gameID = dataAccess.add(new GameData(0, null, null, name, new ChessGame()));
        return gameID;
    }

    public ArrayList<ListGamesGameUnit> listGames() throws ResponseException {
        return dataAccess.listGames();
    }

    public void joinGame(int gameID, String username, String color) throws DataAccessException {
        if (dataAccess.get(gameID) == null){
            throw new ResponseException(400, "Game does not exist");
        }
        if (isColorTaken(gameID, color)){
            throw new ResponseException(403, "Error: already taken");
        }
        dataAccess.updatePlayer(gameID, color, username);
    }

    public void updateGameState(int id, GameData gameData) throws ResponseException {
        dataAccess.updateGameState(id, gameData);
    }

    public boolean isColorTaken(int gameID, String color) throws DataAccessException {
        if (color.equalsIgnoreCase("WHITE")){
            return dataAccess.get(gameID).whiteUsername() != null;
        } else if (color.equalsIgnoreCase("BLACK")) {
            return dataAccess.get(gameID).blackUsername() != null;
        } else {
            throw new DataAccessException("Bad playerColor Request");
        }
    }

    public void clear() throws ResponseException {
        dataAccess.clear();
    }
}

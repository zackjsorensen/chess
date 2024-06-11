package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DatabaseManager;
import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;
import model.GameData;
import model.ListGamesGameUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO extends SQLParentDAO{
    private final String[] createStatement = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `game` longtext,
              PRIMARY KEY (`id`),
              INDEX (gameName)
            )
            """
    };

    public SQLGameDAO() {
        try {
            super.configureDatabase(createStatement);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(Object dataObj) throws ResponseException {
        GameData gameData = (GameData) dataObj;
        String statement = "INSERT INTO games (whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?)";
        String json = new Gson().toJson(gameData.game());
        return executeUpdate(statement, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), json );
    }

    public GameData get(int id) throws ResponseException {

        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT * FROM games WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, id);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");
        ChessGame game = new Gson().fromJson(rs.getString("game"), ChessGame.class);
        return new GameData(id, whiteUsername, blackUsername, gameName, game);
    }

    public ArrayList<ListGamesGameUnit> listGames() throws ResponseException {
        ArrayList<ListGamesGameUnit> gamesList = new ArrayList<ListGamesGameUnit>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT id, whiteUsername, blackUsername, gameName, game FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        GameData temp = readGame(rs);
                        gamesList.add(new ListGamesGameUnit(temp.gameID(), temp.whiteUsername(), temp.blackUsername(), temp.gameName()));
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return gamesList;
    }

    public void updateGameState(int id, GameData gameData) throws ResponseException {
        String json = new Gson().toJson(gameData.game());
        String statement = "UPDATE games SET game = ? WHERE id=?";
        executeUpdate(statement, json, id);
    }

    public void updatePlayer(int id, String color, String username) throws ResponseException {
        String statement;
        if (color.equals("BLACK")){
            statement = "UPDATE games SET blackUsername = ? WHERE id=?";

        } else {
            statement = "UPDATE games SET whiteUsername = ? WHERE id=?";
        }
        executeUpdate(statement, username, id);
    }

    private void delete(int id) throws ResponseException {
        String statement = "DELETE FROM games WHERE id=?";
        executeUpdate(statement, id);
    }

    @Override
    public void clear() throws ResponseException {
        super.clear("games");
    }

}





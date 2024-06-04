package dataaccess.sql;

import dataaccess.DatabaseManager;
import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;
import model.AuthData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO extends SQLParentDAO{
    private final String[] createStatement = {
            """
            CREATE TABLE IF NOT EXISTS auth (
              `authToken` varchar(36) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            )
            """
    };

    public SQLAuthDAO() {
        try {
            super.configureDatabase(createStatement);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Object dataObj) throws ResponseException {
        AuthData auth = (AuthData) dataObj;
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        executeUpdate(statement, auth.authToken(), auth.username());
    }

    @Override
    public AuthData get(Object identifier) throws ResponseException {
        String authToken = (String) identifier;

        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT authToken, username FROM auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }

        return null;
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        String authToken = rs.getString("authToken");
        String username = rs.getString("username");
        return new AuthData(authToken, username);
    }

    @Override
    public void clear() throws ResponseException {
        super.clear("auth");
    }

    public void delete(String authToken) throws ResponseException {
        String statement = "DELETE FROM auth WHERE authToken=?";
        if (this.get(authToken) == null){
            throw new ResponseException(400, "Not logged in");
        }
        executeUpdate(statement, authToken);
    }
}

// can I do better? Something about joining tables, relational databases? HMMM....

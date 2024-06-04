package dataaccess.sql;


import dataaccess.DatabaseManager;
import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;
import model.UserData;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLUserDAO extends SQLParentDAO {

    public SQLUserDAO() {
        try {
            super.configureDatabase(createStatement);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws ResponseException { // watch out for drama there....
        var statement = "TRUNCATE users";
        executeUpdate(statement);
    }

    @Override
    public int add(Object dataObj) throws ResponseException {
        UserData user = (UserData) dataObj;
        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        executeUpdate(statement, user.username(), user.password(), user.email());
        return 0;
    }

    @Override
    public Object get(Object identifier) throws ResponseException {
        String username = (String) identifier;

        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT username, password, email FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }

        return null;
    }

    private Object readUser(ResultSet rs) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        return new UserData(username, password, email);
    }

    private final String[] createStatement = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256),
              PRIMARY KEY (`username`)
            )
            """
    };
}

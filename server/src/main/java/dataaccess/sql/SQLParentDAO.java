package dataaccess.sql;

import dataaccess.DataAccessInterface;
import dataaccess.DatabaseManager;
import dataaccess.exception.DataAccessException;
import dataaccess.exception.ResponseException;

import java.sql.SQLException;
import java.util.Arrays;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public abstract class SQLParentDAO implements DataAccessInterface {
    @Override
    public void clear(String databaseName) throws ResponseException {
        String statement = String.format("TRUNCATE %s", databaseName);
        executeUpdate(statement);
    }

    @Override
    public void add(Object dataObj) throws ResponseException {

    }

    @Override
    public Object get(Object identifier) throws ResponseException {
        return null;
    }

    public int executeUpdate(String statement, Object... params) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        } catch (DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    void configureDatabase(String[] createStatement) throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatement) {
                try (var preparedStatement = conn.prepareStatement((statement))) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}

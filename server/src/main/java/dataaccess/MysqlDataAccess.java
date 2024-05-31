package dataaccess;



public class MysqlDataAccess implements DataAccessInterface{

    public MysqlDataAccess() {
    }

    @Override
    public void clear() {

    }

    @Override
    public void add(Object dataObj) {

    }

    @Override
    public Object get(Object identifier) {
        return null;
    }

    private final String[] createStatements = {
            // not sure if I need ID here.. only reason would be to have foreign keys...
            """
            CREATE TABLE IF NOT EXISTS users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256),
              'email' varchar(256),
              PRIMARY KEY (`username`),
              INDEX(type), // vas es das? 
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private void configureDatabase() throws ResponseException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}

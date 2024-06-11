package model.exception;

public class ResponseException extends DataAccessException {
    public int statusCode;

    public ResponseException( int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}

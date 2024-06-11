package dataaccess;

import model.exception.ResponseException;

public interface DataAccessInterface<T, U> {

    public void clear() throws ResponseException;

    void clear(String databaseName) throws ResponseException;

    public int add(U dataObj) throws ResponseException;

    public U get(T identifier) throws ResponseException;

//    public T getIdentifier(U dataObj);

}

package dataaccess;

public interface DataAccessInterface<T, U> {

    public void clear() throws ResponseException;

    public void add(U dataObj) throws ResponseException;

    public U get(T identifier);

//    public T getIdentifier(U dataObj);

}

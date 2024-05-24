package dataaccess;

public interface DataAccessInterface<T, U> {

    public void clear();

    public void add(U dataObj);

    public U get();

    public T getIdentifier(U dataObj);

}
